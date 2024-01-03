package dev.luisoliveira.esquadrias.enums;

public enum MeasurementUnit {
    KILOGRAM("kg"),
    LITER("L"),
    METER("m"),
    PIECE("pc"),
    GRAM("g"),
    MILLILITER("ml"),
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
