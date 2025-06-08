package com.droute.driverservice.enums;


public enum WeightUnit {
    GRAMS("g"),
    KILOGRAMS("kg"),
    POUNDS("lb"),
    OUNCES("oz"),
    MILLIGRAMS("mg"),
    TONNES("ton");

    private final String abbreviation;

    WeightUnit(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getAbbreviation() {
        return abbreviation;
    }
    
    // Optional: Add method to get display name
    public String getDisplayName() {
        return name().charAt(0) + name().substring(1).toLowerCase();
    }

    // Method to get the enum value from abbreviation
    public static WeightUnit fromAbbreviation(String abbr) {
        for (WeightUnit unit : WeightUnit.values()) {
            if (unit.getAbbreviation().equalsIgnoreCase(abbr)) {
                return unit;
            }
        }
        throw new IllegalArgumentException("No DimensionUnit with abbreviation: " + abbr);
    }
}