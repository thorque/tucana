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
	final void "test whether the ConstellationService returns a list with constellations properly"() {
		def result = []
		2.times {
			result << new Constellation(code: "cd$it")
		}
		def repository = [findAll: {result}] as ConstellationRepository

		Assert.assertEquals(2,
				new ConstellationServiceImpl(constellationRepository: repository).findAllConstellations().size())
	}

	@Test
	final void "test whether the ConstellationService fails at retrieving the list of constellations"() {
		def repository = [findAll: {null}] as ConstellationRepository

		Assert.assertEquals(0,
				new ConstellationServiceImpl(constellationRepository: repository).findAllConstellations().size())
	}
	
	@Test
	final void "test whether the ConstellationService returns a list with constellations with resolved names properly"() {
		def result = []
		2.times {
			result << new Constellation(code: "cd$it", names: [])
		}
		def repository = [findAll: {result}] as ConstellationRepository

		Assert.assertEquals(2,
				new ConstellationServiceImpl(constellationRepository: repository).findAllConstellations().size())
	}
	
	

	@Test
	final void "test whether the ConstellationService can find one constellation by its code"(){
		def constellations = []
		4.times {
			constellations << new Constellation(code: "cd$it")
		}
		def repository = [findByCode: {code -> constellations.find{it.code == code}}] as ConstellationRepository

		Assert.assertEquals("cd2",
				new ConstellationServiceImpl(constellationRepository: repository).findConstellationByCode("cd2").code)
	}

	@Test
	final void "test whether the not finding of a constellation with its code is handled properly"(){
		def repository = [findByCode: {code -> [].find{it.code == code}}] as ConstellationRepository
		Assert.assertNull(new ConstellationServiceImpl(constellationRepository: repository).findConstellationByCode("cd2"))
	}

	@Test
	final void "test if persisting of a constellation object does work"() {
		def repository = [save: {def constellation -> constellation.id = 1; return constellation}] as ConstellationRepository

		Assert.assertEquals(1,
				new ConstellationServiceImpl(constellationRepository: repository).persistConstellation(new
				Constellation()).id)
	}

	@Test
	final void "test if anyone tries to persist a null constellation"() {
		Assert.assertNull(new ConstellationServiceImpl().persistConstellation(null))
	}
}
