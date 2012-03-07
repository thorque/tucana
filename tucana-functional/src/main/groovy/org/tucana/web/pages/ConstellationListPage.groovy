/**
 * 
 */
package org.tucana.web.pages

import geb.Page

/**
 * {@link {@link Page}} to test the ConstellationList
 */
class ConstellationListPage extends Page {
	
	/**
	 * The url of this page
	 */
	static url =  "${System.properties['geb.build.baseUrl']}/constellationList"
	
	/**
	 * Simple check to find out if the correct page was loaded
	 */
	static at = { title == "TUCANA: The list of constellations" }

	static content = {
		/**
		 * Returns the datatable containing the list of all constellations
		 */
		datatable {
			$("table.datatable")
		}
		
		/**
		 * Returns the table rows with the constellation rows (datatable/tbody/tr)
		 */
		results{
			datatable.$("tbody tr")
		}
		
	}
}
