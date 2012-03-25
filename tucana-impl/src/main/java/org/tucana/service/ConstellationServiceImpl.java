package org.tucana.service;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.tucana.domain.Constellation;
import org.tucana.repository.ConstellationRepository;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the ConstellationService interface.
 */
@Service
@Transactional
public class ConstellationServiceImpl implements ConstellationService {
	@Inject
	private ConstellationRepository constellationRepository;

	
	/* (non-Javadoc)
	 * @see org.tucana.service.ConstellationService#findAllConstellations()
	 */
	public List<Constellation> findAllConstellations() {
		List<Constellation> constellations;

		constellations = constellationRepository.findAll(new Sort(
				Direction.ASC, "code"));
		if (constellations == null) {
			constellations = new ArrayList<Constellation>(0);
		}
		return constellations;
	}
	
	/* (non-Javadoc)
	 * @see org.tucana.service.ConstellationService#findAllConstellationsWithNames()
	 */
	@Override
	public List<Constellation> findAllConstellationsWithNames() {
		List<Constellation> constellations;
		
		constellations = findAllConstellations();
		for (Constellation constellation : constellations) {
			constellation.getNames().size();
		}
		
		return constellations;
	}

	/* (non-Javadoc)
	 * @see org.tucana.service.ConstellationService#persistConstellation(org.tucana.domain.Constellation)
	 */
	public Constellation persistConstellation(
			Constellation constellation2Persist) {
		if (constellation2Persist == null) {
			return null;
		}

		constellationRepository.save(constellation2Persist);
		return constellation2Persist;
	}

	/* (non-Javadoc)
	 * @see org.tucana.service.ConstellationService#findConstellationById(java.lang.String)
	 */
	@Override
	public Constellation findConstellationByCode(String code) {
		return constellationRepository.findByCode(code);
	}
}
