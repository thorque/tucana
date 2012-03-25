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
 * @author kamann
 *
 */
@ToString(callSuper = false, includeFieldNames = true, exclude = "id")
@Entity(name = "CONSTELLATION_NAMES")
public class ConstellationName {
	@SuppressWarnings("unused")
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	/**
	 * This field is holding the shortcut for the language
	 * @return The shortcut of the language, eg. <code>de</code> for german, <code>fr</code> for france.
	 */
	@Getter
	private String lang;
	
	/**
	 * This is the name for the constellation for the language
	 * @return The name of the constellation
	 */
	@Getter
	private String name;

}
