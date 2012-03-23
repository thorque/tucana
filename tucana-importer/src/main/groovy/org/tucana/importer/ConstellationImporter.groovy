package org.tucana.importer

import org.htmlcleaner.HtmlCleaner
import org.htmlcleaner.SimpleXmlSerializer
import org.springframework.context.ApplicationContext
import org.springframework.context.support.ClassPathXmlApplicationContext
import org.tucana.domain.Constellation
import org.tucana.service.ConstellationServiceImpl

/**
 * This import retrieves a Wikipedia page with a table of all constellations. This table will be parsed with XPath
 * and the data will be used to create a Constellation Object. With the ConstellationService these Constellation
 * Objects will be stored.
 *
 * @since 1.0.0 M1
 */
class ConstellationImporter {
    private String url = "http://de.wikipedia.org/wiki/Liste_der_Sternbilder"
    private File sqlFile = new File("./target/gen-data/data.sql")
    private File targetFile = new File("../tucana-integration/src/test/resources/database/constellation.sql")

    /**
     * Main entry point of this importer. No args are needed, because this importer is self-configuring.
     * @param args No args are needed
     */
    public static void main(def args) {
        new ConstellationImporter().doImport()
    }

    public void doImport() {
        ConstellationImporter importer = new ConstellationImporter()
        def service = importer.getConstellationService()

        def rows = importer.constellationRows

        if (!sqlFile.exists()) {
            sqlFile.parentFile.mkdirs()
            sqlFile.createNewFile()
        }else{
			sqlFile.text = ""
        }

        rows.eachWithIndex { row, c ->
            importer.createAndPersistConstellation(row, service, c)
        }

        println "Updating data file with the constellation data"
        new AntBuilder().copy(file: sqlFile, tofile: targetFile)


        println "Found ${service.findAllConstellations().size()} constellations."
    }

    /**
     * Builds a Spring {@link ApplicationContext} and retrieve the {@link org.tucana.service.ConstellationService}.
     * @return The Spring-managed {@link org.tucana.service.ConstellationService}
     */
    private ConstellationServiceImpl getConstellationService() {
        ApplicationContext applicationContext =
            new ClassPathXmlApplicationContext("/META-INF/spring/importer-context.xml")

        return applicationContext.getBean("constellationServiceImpl")
    }

    /**
     * Opens the Wikipedia page and tries to separate the table with the constellation data
     * @return A list with the table rows with the {@link Constellation} data
     * @throws RuntimeException Simple exception with the message that no table with constellation data was found
     */
    private def getConstellationRows() {
        def page = getCleanedHtml(url)
        def tables = page.body.'**'.findAll {
            it.name() == "table" && it.@class.text().contains('wikitable sortable')
        }

        if (tables.size() != 1) {
            throw new RuntimeException("Expected only one table. Please check the xpath and the source of wikipedia!")
        }

        def table = tables[0]
        return table.tbody.tr
    }

    /**
     * Takes a row with the {@link Constellation} data, parses the fields and create the {@link Constellation} Object. After
     * this
     * this Object will be persisted.
     * @param row The table row with the data
     * @param service The {@link org.tucana.service.ConstellationService} to use to persist the Constellation Object
     * @param c A simple counter used by the log entry
     */
    private void createAndPersistConstellation(def row, def service, def c) {


        if (!row.@style.text()) {
            def cells = row.td
            Constellation constellation = service.findConstellationByCode(cells[3].text().toLowerCase())
			if (!constellation){
				constellation = new Constellation()
			}
			
            constellation.name = cells[1]
            constellation.genitiveName = cells[2]
            constellation.code = cells[3].text().toLowerCase()
            constellation.hemisphere = cells[4]
            constellation.author = cells[5]
            constellation.authorYear = cells[6].text() as int
            constellation.area = toDouble(cells[7].text())

            println "${c}.: $constellation"
            service.persistConstellation constellation
            createSQLDataScripts(constellation)

        }
    }

    private void createSQLDataScripts(Constellation c) {
        String sql = ""

        sql = "INSERT INTO CONSTELLATIONS VALUES(null, '$c.name', '$c.code', '$c.genitiveName', '$c.hemisphere', " +
                "'$c.author', $c.authorYear, $c.area, null );"
        sqlFile.text += "\n" + sql

    }

    private toDouble(String value) {
        return (value.replaceAll("\\.", "").replace(",", ".")) as double
    }

    /**
     * Uses the {@link HtmlCleaner} to read the Wikipedia HTML and returns valid XML
     * @param url The {@link URL} of the document to read
     * @return A GPathResult with the content of the Wikipedia document
     */
    private def getCleanedHtml(String url) {
        def cleaner = new HtmlCleaner()
        def node = cleaner.clean(url.toURL())

        // Convert from HTML to XML
        def props = cleaner.getProperties()
        def serializer = new SimpleXmlSerializer(props)
        def xml = serializer.getXmlAsString(node)

        new XmlSlurper(false, false).parseText(xml)
    }
}
