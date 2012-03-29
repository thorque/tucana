package org.tucana.service;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner)
@ContextConfiguration
class StarServiceITTest {
	
	@Autowired
	private StarService starService
	
	@Test
	final void "check if all stars for a constellations can be returned"(){
		Assert.assertEquals(11, starService.findAllStarsForConstellation("and").size())
	}

}
