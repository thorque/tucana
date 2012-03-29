/**
 * 
 */
package org.tucana.service;

import java.util.List;

import org.tucana.domain.Star;

/**
 * @author kamann
 *
 */
public interface StarService {
	
	/**
	 * Persist a {@link Star} instance
	 * @param star2Persist The {@link Star} instance to persist
	 * @return The persisted {@link Star} with a filled id (not equal <code>null</code>)
	 */
	Star persistStar(Star star2Persist);
	
	/**
	 * Tries to find all stars for a constellation. Only stars with a filled code property will be returned.
	 * @param constellationCode The 3-char code of a constellation
	 * @return A {@link List} with the stars for the {@link Constellation}
	 * @see Star.getCode()
	 */
	List<Star> findAllStarsForConstellation(String constellationCode);

}
