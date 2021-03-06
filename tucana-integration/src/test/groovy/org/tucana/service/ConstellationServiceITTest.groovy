package org.tucana.service

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner


@RunWith(SpringJUnit4ClassRunner)
@ContextConfiguration
class ConstellationServiceITTest {

    @Autowired
    private ConstellationService service

    @Test
    public final void "test the findAllConstellation service method"() {
        Assert.assertEquals(88, service.findAllConstellations().size())
    }
	
	@Test
	final void "is the list of constellations ordered alphabetically"(){
		def result = service.findAllConstellations()
		
		Assert.assertEquals("and", result[0].code)
		Assert.assertEquals("vul", result[result.size()-1].code)
	}
}
