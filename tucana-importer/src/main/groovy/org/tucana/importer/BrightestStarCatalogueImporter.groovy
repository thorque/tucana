/**
 * 
 */
package org.tucana.importer


import java.io.File;

import groovy.util.logging.Log;

import org.htmlcleaner.HtmlCleaner
import org.htmlcleaner.SimpleXmlSerializer
import org.springframework.context.ApplicationContext
import org.springframework.context.support.ClassPathXmlApplicationContext
import org.tucana.domain.Constellation
import org.tucana.domain.Star;
import org.tucana.importer.tools.DownloadCategory;
import org.tucana.service.ConstellationService;
import org.tucana.service.StarService

/**
 * @author kamann
 *
 */
@Log
class BrightestStarCatalogueImporter {
	private ConstellationService constellationService
	private StarService starService
	private def targetDir = new File("src/main/resources/spider/bsc")
	private File sqlFile = new File("./target/gen-data/bsc.sql")
	private File targetFile = new File("../tucana-api/src/main/resources/db-migration/data/bsc.sql")

	static main(args) {
		new BrightestStarCatalogueImporter().doImport()	
	}
	
	public void doImport() {
		initializeService()
		
		if (!sqlFile.exists()) {
			sqlFile.parentFile.mkdirs()
			sqlFile.createNewFile()
		}else{
			sqlFile.text = ""
		}
		
		def constellations = constellationService.findAllConstellations()
		constellations.each {
			def url = new URL("http://www.alcyone.de/SIT/bsc/${it.code}.html")
			
			if (!targetDir.exists()){
				targetDir.mkdirs()
			}
			
			def spiderDoc = new File(targetDir, "${it.code}.html")
			if (!spiderDoc.exists()){
				use(DownloadCategory){
					spiderDoc << url
				}
			}
			parseDatatable it
		}
		
		new AntBuilder().copy(file: sqlFile, tofile: targetFile)
		
		
	}
	
	def parseDatatable(Constellation constellation){
		
		log.info("----Parsing the brightest stars for $constellation.name")
		
		def page = getCleanedHtml(new File(targetDir, "${constellation.code}.html").toURI().toURL())		
		def rows = page.body.table[1].tbody.tr.findAll{
			it.@class.text().startsWith("TableCellStyle")
		}
		
		rows.each{
			Star star = new Star()
			star.code = constellation.code
			star.hr = asInt(it.td[0].a.text().trim(), null)
			star.name = it.td[1].text().trim()
			star.bayer = it.td[2].text().trim()
			star.flamsteed = asInt(it.td[3].text().trim(), null)
			star.hd = asInt(it.td[4].text().trim(), null)
			star.sao = asInt(it.td[5].text().trim(), null)
			star.fk5 = asInt(it.td[6].text().trim(), null)
			star.varId = it.td[7].text().trim()
			star.ra = asRa(it.td[8].text().trim())
			star.de = asDe(it.td[9].text().trim())
			star.pmRa = it.td[10].text().trim() as double
			star.pmDe = it.td[11].text().trim() as double
			star.parsec = asDouble(it.td[12].text().trim() , null)
			star.mag = asDouble(it.td[13].text().trim(), null)
			star.mk = it.td[14].text().trim() 
			star.multiple = asInt(it.td[15].text().trim() , null)
			starService.persistStar(star)
			createSQLDataScripts(star)
			
		}
	}
	
	private void createSQLDataScripts(Star s) {
		String sql = ""
	
		sql = """INSERT INTO Stars VALUES($s.id, '$s.code', '$s.name', $s.hr,  '$s.bayer', $s.flamsteed, $s.hd, 
					$s.sao, $s.fk5, '$s.varId', $s.ra, $s.de, $s.pmRa, $s.pmDe, $s.parsec, $s.mag, '$s.mk', $s.multiple);"""
		sqlFile.text += "\n" + sql
	}
	
	private def asRa(String ra){
		def x = ra.replaceAll(" ", "")
		
		int h = x.substring(0, x.indexOf("h")) as int
		int m = x.substring(x.indexOf("h")+1, x.indexOf("m")) as int
		double s = x.substring(x.indexOf("m")+1, x.indexOf("s")) as double
		(((s / 60) + m) / 60) + h
	}
	
	private def asDe(String de){
		def y = de.replaceAll(" ", "")
		
		def ds = y.substring(0, 3) 
		try{
			ds.substring(1) as int
		}catch(NumberFormatException ex){
			ds = ds.substring(0, 2)
		}
		int d = (ds.startsWith("+"))? ds.substring(1) as int: (ds.substring(1) as int)*-1
		int m = y.substring(ds.length()+1, y.indexOf("'")) as int
		double s = y.substring(y.indexOf("'")+1, y.indexOf("\"")) as double
		(((s / 60) + m) / 60) + d
	}
	
	private def asInt(String value, def defaultValue){
		(value)? value as int: defaultValue
	}
	
	private def asDouble(String value, def defaultValue){
		(value)? value as double: defaultValue
	}
	
	/**
	* Builds a Spring {@link ApplicationContext} and initialize the {@link org.tucana.service.ConstellationService} and
	* {@link {@link StarService}}
	*/
   private def initializeService() {
	   ApplicationContext applicationContext =
			   new ClassPathXmlApplicationContext("/META-INF/spring/importer-context.xml")
			   
	   constellationService = (ConstellationService) applicationContext.getBean("constellationServiceImpl")
	   starService = (StarService) applicationContext.getBean("starServiceImpl")
   }
   
   /**
   * Uses the {@link HtmlCleaner} to read the Wikipedia HTML and returns valid XML
   * @param url The {@link URL} of the document to read
   * @return A GPathResult with the content of the Wikipedia document
   */
  private def getCleanedHtml(URL url) {
	  def cleaner = new HtmlCleaner()
	  def node = cleaner.clean(url)

	  def props = cleaner.getProperties()
	  def serializer = new SimpleXmlSerializer(props)
	  def xml = serializer.getXmlAsString(node)

	  new XmlSlurper(false, false).parseText(xml)
  }

}
