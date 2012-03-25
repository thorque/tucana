/**
 * 
 */
package org.tucana.importer

import java.io.File;

import groovy.xml.StreamingMarkupBuilder

import org.htmlcleaner.HtmlCleaner
import org.htmlcleaner.SimpleXmlSerializer
import org.springframework.context.ApplicationContext
import org.springframework.context.support.ClassPathXmlApplicationContext
import org.tucana.domain.Constellation;
import org.tucana.service.ConstellationService

/**
 * @author kamann
 *
 */
class ConstellationDescriptionImporter {
	private File sqlFile = new File("./target/gen-data/constellation_details.sql")
	private File targetFile = new File("../tucana-integration/src/test/resources/database/constellation_details.sql")
	
	public static void main(def args){
		new ConstellationDescriptionImporter().doImport()
	}
	
	public void doImport() {
		ConstellationDescriptionImporter importer = new ConstellationDescriptionImporter()
		def service = importer.getConstellationService()
		
		if (!sqlFile.exists()) {
			sqlFile.parentFile.mkdirs()
			sqlFile.createNewFile()
		}else{
			sqlFile.text = ""
		}
		
		def constellations = service.findAllConstellationsWithNames()
		constellations.each {
			it.description =  getAbstract(it)
			println it.description
			service.persistConstellation(it)
			createSQLDataScripts it
		}
		
		new AntBuilder().copy(file: sqlFile, tofile: targetFile)
	}
	
	private void createSQLDataScripts(Constellation c) {
		String sql = ""
		
		String description = c.description
		description = description.replaceAll("'", "\"")

		sql = "UPDATE CONSTELLATIONS SET description = '${description}' WHERE code = '${c.code}';"
		sqlFile.text += "\n" + sql

	}
	
	
	private String getAbstract(org.tucana.domain.Constellation constellation){
		
		def name = getName(constellation)
		
		def page = getCleanedHtml("http://en.wikipedia.org/wiki/${name}")
		def abstr = page.body.'**'.findAll {
			it.name() == "div" && it.@class.text().contains('mw-content-ltr')
		}

		def ptr = abstr[0].children().find{
			it.name ()== "p"
		}
		def text = new StreamingMarkupBuilder().bindNode(ptr).toString()
		text = text.replaceAll("/wiki/", "wiki/")
		
		if (text.startsWith("<p>")){
			text = text.substring(3)
		}
		
		if (text.endsWith("</p>")){
			text = text.substring(0, text.length()-4)
		}
	}
	
	private String getName(Constellation constellation){
		def corrections = [boo: URLEncoder.encode("Boštes", "UTF-8"),
			lac: "Lacerta"]
		def name = (corrections[constellation.code])? corrections[constellation.code]: constellation.name+"_(constellation)"
		
		println name
		name.replaceAll(" ", "_")
	}
	
	/**
	* Builds a Spring {@link ApplicationContext} and retrieve the {@link org.tucana.service.ConstellationService}.
	* @return The Spring-managed {@link org.tucana.service.ConstellationService}
	*/
   private ConstellationService getConstellationService() {
	   ApplicationContext applicationContext =
		   new ClassPathXmlApplicationContext("/META-INF/spring/importer-context.xml")

	   return applicationContext.getBean("constellationServiceImpl")
   }
   
   /**
   * Uses the {@link HtmlCleaner} to read the Wikipedia HTML and returns valid XML
   * @param url The {@link URL} of the document to read
   * @return A GPathResult with the content of the Wikipedia document
   */
  private def getCleanedHtml(String url) {
	  def cleaner = new HtmlCleaner()
	  def node = cleaner.clean(url.toURL(), "UTF-8")
	 
	  def props = cleaner.getProperties()
	  def serializer = new SimpleXmlSerializer(props)
	  def xml = serializer.getXmlAsString(node, "UTF-8")

	  new XmlSlurper(false, false).parseText(xml)
  }

}
