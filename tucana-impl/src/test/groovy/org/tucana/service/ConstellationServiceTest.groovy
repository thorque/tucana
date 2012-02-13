package org.tucana.service

import org.junit.Assert
import org.junit.Test
import org.tucana.domain.Constellation
import org.tucana.repository.ConstellationRepository

/**
 * Testcases for the ConstellationServiceImpl
 */
class ConstellationServiceTest {

    @Test
    final void "test if ConstellationService returns a list with constellations properly"() {
        def result = []
        2.times {
            result << new Constellation(code: "cd$it")
        }
        def repository = [findAll: {result}] as ConstellationRepository

        Assert.assertEquals(2,
                new ConstellationServiceImpl(constellationRepository: repository).findAllConstellations().size())
    }

    @Test
    final void "test if ConstellationService fails at retrieving the list of constellations"() {
        def repository = [findAll: {null}] as ConstellationRepository

        Assert.assertEquals(0,
                new ConstellationServiceImpl(constellationRepository: repository).findAllConstellations().size())
    }
}
