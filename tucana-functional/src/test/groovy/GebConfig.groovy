/*
	This is the Geb configuration file.
	
	See: http://www.gebish.org/manual/current/configuration.html
*/

import org.openqa.selenium.htmlunit.HtmlUnitDriver

// Use htmlunit as the default
// See: http://code.google.com/p/selenium/wiki/HtmlUnitDriver
driver = { 
	def driver = new HtmlUnitDriver()
	driver.javascriptEnabled = true
	driver
}
System.properties['geb.build.baseUrl'] ="http://localhost:8080/tucana-web"

environments {
	
}