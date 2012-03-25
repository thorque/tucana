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
	
	@Test
	final void "check whether all names of the supported 5 languages are shown on the detailpage"(){
		Browser.drive(getBrowser()){
			to ConstellationListPage
			
			Assert.assertTrue at(ConstellationListPage)
			
			detail_link("AND").click()
			Assert.assertTrue at(ConstellationDetailPage)			
			Assert.assertEquals 5, lang_data.size()
			Assert.assertEquals "EN", lang_data[0].text()
		}
	}
	
	@Test
	final void "check whether the names of Antlia are shown correctly"(){
		Browser.drive(getBrowser()){
			to ConstellationListPage
			
			Assert.assertTrue at(ConstellationListPage)
			
			detail_link("ANT").click()
			Assert.assertTrue at(ConstellationDetailPage)
			
			Assert.assertEquals "EN", lang_data[0].text()
			Assert.assertEquals "Air Pump", name_data[0].text()
			
			Assert.assertEquals "DE", lang_data[1].text()
			Assert.assertEquals "Luftpumpe", name_data[1].text()
			
			Assert.assertEquals "FR", lang_data[2].text()
			Assert.assertEquals "Machine Pneumatique", name_data[2].text()
			
			Assert.assertEquals "IT", lang_data[3].text()
			Assert.assertEquals "Macchina Pneumatica", name_data[3].text()
			
			Assert.assertEquals "SU", lang_data[4].text()
			Assert.assertEquals "Ilmapumppu", name_data[4].text()
		}
	}

}
