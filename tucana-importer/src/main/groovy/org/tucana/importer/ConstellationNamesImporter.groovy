/**
 * 
 */
package org.tucana.importer

import org.hibernate.Hibernate
import org.htmlcleaner.HtmlCleaner
import org.htmlcleaner.SimpleXmlSerializer
import org.springframework.context.ApplicationContext
import org.springframework.context.support.ClassPathXmlApplicationContext
import org.tucana.domain.ConstellationName
import org.tucana.service.ConstellationService
import org.tucana.service.ConstellationServiceImpl

/**
 * @author kamann
 *
 */
class ConstellationNamesImporter {
	private dataWithNamedConstellations

	static main(args) {
		new ConstellationNamesImporter().doImport()
	}

	public void doImport() {
		def service = getConstellationService()
		def constellations = service.findAllConstellationsWithNames()

		
		constellations.each{
			Hibernate.initialize(it)
			def names = getAllNamesForConstellation(it.code)
			it.names = []
			service.persistConstellation(it)
			names.each{key, value ->
				it.names << new ConstellationName(lang: key, name: value)
			}
			service.persistConstellation(it)
		}
	}

	private Map getAllNamesForConstellation(String code){
		if (!dataWithNamedConstellations){
			def page = getCleanedHtml("http://de.wikipedia.org/wiki/Liste_der_Sternbilder_in_verschiedenen_Sprachen")
			dataWithNamedConstellations = page.body.'**'.find { it.name() == "table" && it.@class.text().contains("wikitable sortable") }.tbody.tr
		}

		println code
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
