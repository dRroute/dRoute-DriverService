package com.droute.driverservice.enums;

public enum DimensionUnit {
    CENTIMETERS("cm"),  // "cm" is passed to constructor
    METERS("m"),        // "m" is passed to constructor
    INCHES("in"),       // "in" is passed to constructor
    FEET("ft"),         // "ft" is passed to constructor
    MILLIMETERS("mm");  // "mm" is passed to constructor

    private final String abbreviation;  // Each enum value stores its abbreviation

    // Enum constructor
    DimensionUnit(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    // Method to get the abbreviation
    public String getAbbreviation() {
        return abbreviation;
    }
}