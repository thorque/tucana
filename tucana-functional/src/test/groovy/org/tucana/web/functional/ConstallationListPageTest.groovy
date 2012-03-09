package org.tucana.web.functional;

import static org.junit.Assert.*;
import geb.Browser
import geb.junit4.GebTest;


import org.junit.Assert
import org.junit.Test;
import org.tucana.web.pages.ConstellationListPage;

class ConstallationListPageTest  extends GebTest{
	
	@Test
	final void "test if the ConstallationListPage returns 88 constellations"(){
		Browser.drive(getBrowser()) {			
			to ConstellationListPage			
			
			Assert.assertTrue(at(ConstellationListPage))
			Assert.assertEquals(88, results.size())
		}
	}

}
