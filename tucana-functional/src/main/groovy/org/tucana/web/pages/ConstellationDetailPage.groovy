/**
 * 
 */
package org.tucana.web.pages

import geb.Page
import groovy.lang.MetaClass;

/**
 * Page representation of the ConstellationDetail page
 * 
 * @author Thorsten Kamann
 */
class ConstellationDetailPage extends Page {
	/**
	* The url of this page
	*/
   static url =  "${System.properties['geb.build.baseUrl']}/constellationDetail"
   
   /**
	* Simple check to find out if the correct page was loaded
	*/
   static at = { title == "TUCANA: Details of the constellation" }
   
   static content = {
	   /**
	    * @returns The table with the css-class <code>detailview</code>
	    */
	   dataview{
		   $("table.detailview")
	   }
	   
	   /**
	   * @returns The table with the css-class <code>namesview</code>
	   */
	  namesview{
		  $("table.namesview")
	  }
	   
	   /**
	    * Generic closure to access any td in the detailview table
	    * @param row_index The index of the row based on a 0-index
	    * @param cell_index The index of the cell (td) based on a 0-index
	    * @return The cell (in html is this a td) with the given coordinates
	    */
	   cell_data{row_index, cell_index ->
		   dataview.$("tbody tr")[row_index].find("td")[cell_index]
	   }
	   
	   lang_data{
		   namesview.$("tbody tr th")
	   }
	   
	   name_data{
		   namesview.$("tbody tr td")
	   }
	   
   }
   
	

}
