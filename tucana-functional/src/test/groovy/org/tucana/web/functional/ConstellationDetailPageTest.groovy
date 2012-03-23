package org.tucana.web.functional;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;
import org.tucana.web.pages.ConstellationDetailPage;
import org.tucana.web.pages.ConstellationListPage;

import geb.Browser;
import geb.junit4.GebTest;

class ConstellationDetailPageTest extends GebTest{
	
	@Test
	final void "navigate to the detail page of andromeda"(){
		Browser.drive(getBrowser()) {
			to ConstellationListPage
			
			Assert.assertTrue at(ConstellationListPage)
			
			detail_link("AND").click()
			Assert.assertTrue at(ConstellationDetailPage)
			
			Assert.assertEquals "AND", cell_data(0, 1).text()
		}
	}
	
	@Test
	final void "check whether the description from wikipedia exists for Andromeda"(){
		Browser.drive(getBrowser()){
			to ConstellationListPage
			
			Assert.assertTrue at(ConstellationListPage)
			
			detail_link("AND").click()
			Assert.assertTrue at(ConstellationDetailPage)
			
			Assert.assertTrue cell_data(1, 0).text().contains("Andromeda")
		}
	}

}
