package org.tucana.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.ToString;

/**
 * A constellation is a group of stars with a unique name. There are 88 constellations defined by the IAU.
 *
 * @since 1.0.0
 */
@ToString(callSuper = false, includeFieldNames = true, exclude = "id")
@Entity(name = "CONSTELLATIONS")
public class Constellation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    private Long id;

    /**
     * Every constellation has its own unique code. This code will be returned here.
     *
     * @return The unique code of the constellation
     */
    @Getter
    private String code;

    /**
     * The name of a constellation is in the latin language
     *
     * @return The latin name of the constellation
     */
    @Getter
    private String name;
    
    /**
     * @return A {@link List} with the constellation names in the foreign language
     */
    @OneToMany(cascade=CascadeType.ALL)
    @Getter
    private List<ConstellationName> names = new ArrayList<ConstellationName>();

    /**
     * Every constellation needs the genitive variant of its name. This is used for the title of the brightest stars
     * in a constellation (see Bayer names)
     *
     * @return The name in the genitive form
     */
    @Getter
    private String genitiveName;
    
    @Getter
    @Column(nullable=true)
    @Lob
    private String description;

    /**
     * The 88 constellation can be divided into 3 groups:
     * <ul>
     * <li>Constellations reside in the northern hemisphere</li>
     * <li>Constellations reside on the southern hemisphere</li>
     * <li>Constellations reside on the northern and southern hemisphere</li>
     * </ul>
     *
     * @return The hemisphere this constellation reside. Valid values are <code>N</code>, <code>S</code>,
     * <code>N S</code>
     */
    @Getter
    private String hemisphere;

    /**
     * The most constellations have an unique author. This is a person mentioned a constellation at the first time.
     *
     * @return The name of the author
     */
    @Getter
    private String author;

    /**
     * Additional to the author name there is a year the constellation was mentioned by the author for the fist time.
     *
     * @return The year this constellation was mentioned for the first time by the author
     */
    @Getter
    private int authorYear;

    /**
     * The area of the sky the constellation hold in (°)²
     *
     * @return The area this constellation hold
     */
    @Getter
    private double area;


}
