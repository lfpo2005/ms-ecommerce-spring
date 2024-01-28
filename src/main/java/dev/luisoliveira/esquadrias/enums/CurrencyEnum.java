package dev.luisoliveira.esquadrias.enums;

public enum CurrencyEnum {

    USD("USD"),
    EUR("EUR"),
    BRL("BRL"),
    BTC("BTC");

    private String code;

    CurrencyEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}