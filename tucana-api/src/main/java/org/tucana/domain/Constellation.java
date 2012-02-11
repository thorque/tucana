package org.tucana.domain;

/**
 * A constellation is a group of stars with a unique name. There are 88 constellations defined by the IAU.
 *
 * @since 1.0.0
 */
public interface Constellation {

    /**
     * Every constellation has its own unique code. This code will be returned here.
     *
     * @return The unique code of the constellation
     */
    String getCode();

    /**
     * The name of a constellation is in the latin language
     *
     * @return The latin name of the constellation
     */
    String getName();

    /**
     * Evey constellation needs the genitive variant of its name. This is used for the title of the brightest stars
     * in a constellation (see Bayer names)
     *
     * @return The name in the genitive form
     */
    String getGenitiveName();

    /**
     * The 88 constellation can be divided into 3 groups:
     * <ul>
     * <li>Constellations reside in the northern hemisphere</li>
     * <li>Constellations reside on the southern hemisphere</li>
     * <li>Constellations reside on the northern and southern hemisphere</li>
     * </ul>
     *
     * @return The hemisphere this constellation reside. Valid values are <code>N</code>, <code>S</code>,
     *         <code>N S</code>
     */
    String getHemisphere();

    /**
     * The most constellations have an unique author. This is a person mentioned a constellation at the first time.
     *
     * @return The name of the author
     */
    String getAuthor();

    /**
     * Additional to the author name there is a year the constellation was mentioned by the author for the fist time.
     *
     * @return The year this constellation was mentioned for the first time by the author
     */
    int getAuthorYear();

    /**
     * The area of the sky the constellation hold in (°)²
     *
     * @return The area this constellation hold
     */
    double getArea();
}
