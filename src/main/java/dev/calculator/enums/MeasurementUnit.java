package dev.calculator.enums;

public enum MeasurementUnit {
    KILOGRAM("kg"),
    LITER("L"),
    METER("mt"),
    PIECE("pc"),
    GRAM("gr"),
    MILLILITER("mm"),
    CENTIMETER("cm"),
    BOX("box"),
    BAG("bag");

    private final String abbreviation;

    MeasurementUnit(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getAbbreviation() {
        return abbreviation;
    }
}
