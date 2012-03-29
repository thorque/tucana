/**
 * 
 */
package org.tucana.importer

import java.io.File;

import org.hibernate.Hibernate
import org.htmlcleaner.HtmlCleaner
import org.htmlcleaner.SimpleXmlSerializer
import org.springframework.context.ApplicationContext
import org.springframework.context.support.ClassPathXmlApplicationContext
import org.tucana.domain.Constellation
import org.tucana.domain.ConstellationName
import org.tucana.service.ConstellationService
import org.tucana.service.ConstellationServiceImpl

/**
 * @author kamann
 *
 */
class ConstellationNamesImporter {
	private dataWithNamedConstellations
	private File sqlFile = new File("./target/gen-data/constellation_names.sql")
	private File targetFile = new File("../tucana-api/src/main/resources/db-migration/data/constellation_names.sql")

	static main(args) {
		new ConstellationNamesImporter().doImport()
	}

	public void doImport() {
		def service = getConstellationService()
		def constellations = service.findAllConstellationsWithNames()

		if (!sqlFile.exists()) {
			sqlFile.parentFile.mkdirs()
			sqlFile.createNewFile()
		}else{
			sqlFile.text = ""
		}
		
		constellations.each{
			Hibernate.initialize(it)
			def names = getAllNamesForConstellation(it.code)
			it.names = []
			service.persistConstellation(it)
			names.each{key, value ->
				def cn = new ConstellationName(lang: key, name: value)
				it.names << cn
			}
			service.persistConstellation(it)
		}
		
		constellations = service.findAllConstellationsWithNames()
		constellations.each{
			createSQLDataScripts(it)
		}
		new AntBuilder().copy(file: sqlFile, tofile: targetFile)
	}

	private Map getAllNamesForConstellation(String code){
		if (!dataWithNamedConstellations){
			def page = getCleanedHtml("http://de.wikipedia.org/wiki/Liste_der_Sternbilder_in_verschiedenen_Sprachen")
			dataWithNamedConstellations = page.body.'**'.find { it.name() == "table" && it.@class.text().contains("wikitable sortable") }.tbody.tr
		}

		println "Processing code"
		def row = dataWithNamedConstellations.'**'.find { it.name = "td" && it.text().toLowerCase() == "$code" }.parent()
		Map names = [:]
		names.de = row.td[1].text()
		names.en = row.td[5].text()
		names.su = row.td[6].text()
		names.fr = row.td[7].text()
		names.'it' = row.td[8].text()
		//names.es = row.td[12].text()
		return names
	}
	
	private void createSQLDataScripts(Constellation c) {
		String sql = ""
		
		c.names.each{
			def name = it.name
			name = name.replaceAll("'", "´")
			sql = "INSERT INTO Constellation_Names VALUES($it.id, '$it.lang', '${name}');\n"
			sql += "INSERT INTO CONSTELLATIONS_CONSTELLATION_NAMES VALUES($c.id, $it.id);"
			sqlFile.text += "\n" + sql
		}

	}

	/**
	 * Builds a Spring {@link ApplicationContext} and retrieve the {@link org.tucana.service.ConstellationService}.
	 * @return The Spring-managed {@link org.tucana.service.ConstellationService}
	 */
	private ConstellationService getConstellationService() {
		ApplicationContext applicationContext =
				new ClassPathXmlApplicationContext("/META-INF/spring/importer-context.xml")

		def proxy = (ConstellationService) applicationContext.getBean("constellationServiceImpl")
		
		return proxy
	}

	/**
	 * Uses the {@link HtmlCleaner} to read the Wikipedia HTML and returns valid XML
	 * @param url The {@link URL} of the document to read
	 * @return A GPathResult with the content of the Wikipedia document
	 */
	private def getCleanedHtml(String url) {
		def cleaner = new HtmlCleaner()
		def node = cleaner.clean(url.toURL())

		def props = cleaner.getProperties()
		def serializer = new SimpleXmlSerializer(props)
		def xml = serializer.getXmlAsString(node)

		new XmlSlurper(false, false).parseText(xml)
	}
}
