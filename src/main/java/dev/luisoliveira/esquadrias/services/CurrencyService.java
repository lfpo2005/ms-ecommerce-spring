package dev.luisoliveira.esquadrias.services;

import dev.luisoliveira.esquadrias.models.CurrencyModel;

import java.util.Map;

public interface CurrencyService {


    Map<String, CurrencyModel> getCurrency(String currencyEnum);
}
