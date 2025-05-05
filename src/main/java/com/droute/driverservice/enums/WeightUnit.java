package com.droute.driverservice.enums;


public enum WeightUnit {
    GRAMS("g"),
    KILOGRAMS("kg"),
    POUNDS("lb"),
    OUNCES("oz"),
    MILLIGRAMS("mg"),
    TONNES("t");

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
}