package dev.luisoliveira.esquadrias.dtos.resp;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import dev.luisoliveira.esquadrias.models.CurrencyModel;

import java.util.HashMap;
import java.util.Map;

public class CurrencyResponse {

    private Map<String, CurrencyModel> currencies = new HashMap<>();

    @JsonAnySetter
    public void setCurrencies(String key, CurrencyModel value) {
        this.currencies.put(key, value);
    }

    public Map<String, CurrencyModel> getCurrencies() {
        return currencies;
    }
}
