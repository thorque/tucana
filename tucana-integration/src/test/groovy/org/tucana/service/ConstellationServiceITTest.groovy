package org.tucana.service

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

/**
 * Created by IntelliJ IDEA.
 * User: kamann
 * Date: 13.02.12
 * Time: 21:40
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner)
@ContextConfiguration
class ConstellationServiceITTest {

    @Autowired
    private ConstellationService service

    @Test
    public final void "test the findAllConstellation service method"() {
        Assert.assertEquals(2, service.findAllConstellations().size())
    }
}
