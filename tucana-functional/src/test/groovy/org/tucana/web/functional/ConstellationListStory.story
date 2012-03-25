package org.tucana.web.functional

import org.tucana.web.pages.ConstellationListPage;

using "geb"

scenario "simple",{
	
	when "browse to the ConstellationList page", {
		to ConstellationListPage
		
		
	}
	
	then "i am there", {
		at ConstellationListPage
	}
	
	and "there should be 88 entries",{
		println page.results.size().shouldBe 88
	}
}