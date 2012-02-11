package org.tucana.service;

import org.tucana.domain.Constellation;

import java.util.List;

/**
 * This service encapsulates all public method need to work with constellations
 *
 * @since 1.0.0 M1
 */
public interface ConstellationService {

    /**
     * This service method finds all constellation. This should be 88.
     *
     * @return A list with all Constellation
     */
    List<Constellation> findAllConstellations();
}
