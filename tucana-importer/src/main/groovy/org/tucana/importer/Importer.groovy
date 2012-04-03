/**
 * 
 */
package org.tucana.importer

/**
 * @author kamann
 *
 */
@groovy.util.logging.Log
class Importer {


	static main(args) {
		new Importer().doImport()
	}

	def doImport(){
		
		log.info("Starting the TUCANA Importer")
		
		def importerClasses = ["ConstellationImporter", 
			"ConstellationDescriptionImporter", 
			"ConstellationNamesImporter",
			"BrightestStarCatalogueImporter"]
		
		importerClasses.each {
			("org.tucana.importer.$it" as Class).newInstance().doImport()
		}
	}
}
