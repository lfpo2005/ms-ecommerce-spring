package dev.calculator.services;

import dev.calculator.models.CurrencyModel;

import java.util.Map;

public interface CurrencyService {


    Map<String, CurrencyModel> getCurrency(String currencyEnum);
}
