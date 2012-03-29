/**
 * 
 */
package org.tucana.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.ToString;

/**
 * A star is a skyobject like our sun. This class describes a star with many information, eg. the name or coding.
 * Additional there are additional information about the position and the spectral type.
 * 
 * @author Thorsten Kamann
 */
@ToString(callSuper = false, includeFieldNames = true, exclude = "id")
@Entity(name = "STARS")
public class Star {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    private Long id;
	
	/**
	 * The code of the constellation this star belongs to.
	 * @return The 3-char code of the {@link Constellation}
	 * @see Constellation
	 */
	@Getter
	private String code;
	
	/**
	 * @return The Harvard Revised Number. This is the ordered number of the Brightest Star Catalogue.
	 */
	@Getter
	private Integer hr;
	
	/**
	 * @return Some stars have their own name. This name will be returned here. 
	 */
	@Getter
	private String name;
	
	/**
	 * The brightest stars of a constellation are named with the chars of the greek alphabet. 
	 * @return the Bayer name of the star if one exists, otherwise <code>null</code>.
	 */
	@Getter
	private String bayer;
	
	/**
	 * The Bayer notation is very limited because of the max 24 char of the greek alphabet. For this there is the 
	 * Flamsteed notation. This notation numbers the stars in a constellation.
	 * @return The Flamsteed number of the star or <code>null</code> if no exists
	 */
	@Getter
	private Integer flamsteed;
	
	/**
	 * @return Henry Draper Catalog Number or <code>null</code> if no exists
	 */
	@Getter
	private Integer hd;
	
	/**
	 * @return The SAO catalog number or <code>null</code> if no exists
	 */
	@Getter
	private Integer sao;
	
	/**
	 * @return The FK5 catalog number or <code>null</code> if no exists
	 */
	@Getter
	private Integer fk5;
	
	/**
	 * If this star is a variable star this returns the variable star id
	 * @return The variable star id or an empty string if no name is given
	 */
	@Getter
	private String varId;
	
	/**
	 * @return The right ascension for J2000
	 */
	@Getter
	private Double ra;
	
	/**
	 * @return The declination for J2000
	 */
	@Getter
	private Double de;
	
	/**
	 * @return The proper motion (right ascension) of this star 
	 */
	@Getter
	private Double pmRa;
	
	/**
	 * @return The proper motion (right ascension) of this star 
	 */
	@Getter
	private Double pmDe;
	
	/**
	 * @return The trigonometric parallax or <code>null</code> if no one given
	 */
	@Getter
	private Double parsec;
	
	/**
	 * return The relative magnitude of the star
	 */
	@Getter
	private Double mag;
	
	/**
	 * @return The spectral type
	 */
	@Getter
	private String mk;
	
	/**
	 * @return The number of components if this star is a multiple star or <code>null</code> if not
	 */
	@Getter
	private Integer multiple;

}
