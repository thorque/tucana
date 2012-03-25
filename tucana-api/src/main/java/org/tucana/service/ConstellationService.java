package org.tucana.service;

import java.util.List;

import org.tucana.domain.Constellation;

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

	/**
	 * This service method finds all constellation. This should be 88. The
	 * difference between this method and findAllConstellations is that all
	 * names are resolved.
	 * 
	 * @return A list with all Constellation
	 */
	List<Constellation> findAllConstellationsWithNames();

	/**
	 * With this service method you get access to one constellation with the
	 * given code.
	 * 
	 * @param code
	 *            The code of the constellation you want to get
	 * @return The {@link Constellation} with the given code
	 */
	Constellation findConstellationByCode(final String code);

	/**
	 * Persist a constellation
	 * 
	 * @param constellation2Persist
	 *            The {@link Constellation} instance to persist
	 * @return The persisted {@link Constellation}. If the operation was
	 *         successfully the id should'nt be <code>null</code>.
	 */
	public Constellation persistConstellation(
			Constellation constellation2Persist);
}
