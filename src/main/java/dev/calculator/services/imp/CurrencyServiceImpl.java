package dev.calculator.services.imp;

import dev.calculator.integratins.AwesomeApiIntegration;
import dev.calculator.dtos.resp.CurrencyResponse;
import dev.calculator.models.CurrencyModel;
import dev.calculator.services.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CurrencyServiceImpl implements CurrencyService {


    @Autowired
    AwesomeApiIntegration awesomeApiIntegration;

    @Override
    public Map<String, CurrencyModel> getCurrency(String currencyEnum) {
        CurrencyResponse response = awesomeApiIntegration.getCurrency(currencyEnum);
        return response.getCurrencies();
    }
}
