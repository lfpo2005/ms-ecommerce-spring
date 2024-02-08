package dev.ecommerce.services.imp;

import dev.ecommerce.integratins.AwesomeApiIntegration;
import dev.ecommerce.dtos.resp.CurrencyResponse;
import dev.ecommerce.models.CurrencyModel;
import dev.ecommerce.services.CurrencyService;
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
