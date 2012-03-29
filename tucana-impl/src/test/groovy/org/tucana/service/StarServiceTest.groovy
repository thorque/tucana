package org.tucana.service

import static org.junit.Assert.*

import org.junit.Assert
import org.junit.Test
import org.tucana.domain.Star
import org.tucana.repository.StarRepository

class StarServiceTest {

	@Test
	final void "check if the findAll operation returns all persisted stars"(){
		def data = []
		5.times{
			data << new Star(name: "name$it", code: (it%2==0)?"and":"abc" )
		}
		def repository = [findByCode:{data.findAll{it.code=="and"}}] as StarRepository

		Assert.assertEquals(3, new StarServiceImpl(starRepository:repository).findAllStarsForConstellation("and").size())
	}
}
