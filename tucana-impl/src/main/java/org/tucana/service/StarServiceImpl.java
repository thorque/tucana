/**
 * 
 */
package org.tucana.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tucana.domain.Star;
import org.tucana.repository.StarRepository;

/**
 * @author kamann
 *
 */
@Service
@Transactional
public class StarServiceImpl implements StarService {
	
	@Inject
	private StarRepository starRepository;

	@Override
	public Star persistStar(Star star2Persist) {
		return starRepository.saveAndFlush(star2Persist);
	}

	@Override
	public List<Star> findAllStarsForConstellation(String constellationCode) {
		List<Star> stars;
		
		stars = starRepository.findByCode(constellationCode);
		if (stars == null){
			stars = new ArrayList<Star>();
		}
		return stars;
	}

}
