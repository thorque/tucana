package org.tucana.domain;

import lombok.Getter;
import lombok.ToString;

/**
 * This is the concrete implementation of the Constellation interface
 *
 * @since 1.0.0 M1
 */
@ToString(callSuper = false, includeFieldNames = true)
public class ConstellationImpl implements Constellation {
    @Getter
    private String code;

    @Getter
    private String name;

    @Getter
    private String genitiveName;

    @Getter
    private String hemisphere;

    @Getter
    private String author;

    @Getter
    private int authorYear;

    @Getter
    private double area;


}
