package dev.ecommerce.services;

import dev.ecommerce.models.CurrencyModel;

import java.util.Map;

public interface CurrencyService {


    Map<String, CurrencyModel> getCurrency(String currencyEnum);
}
