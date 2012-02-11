package org.tucana.service;

import org.tucana.domain.Constellation;
import org.tucana.repository.ConstellationRepository;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the ConstellationService interface.
 */
public class ConstellationServiceImpl implements ConstellationService {
    @Inject
    private ConstellationRepository constellationRepository;

    /**
     * This service method finds all constellation. This should be 88.
     *
     * @return A list with all Constellation
     */
    public List<Constellation> findAllConstellations() {
        List<Constellation> constellations;

        constellations = constellationRepository.findAll();
        if (constellations == null) {
            constellations = new ArrayList<Constellation>(0);
        }
        return constellations;
    }
}
