package dev.luisoliveira.esquadrias.services.imp;

import dev.luisoliveira.esquadrias.dtos.resp.CurrencyResponse;
import dev.luisoliveira.esquadrias.integratins.AwesomeApiIntegration;
import dev.luisoliveira.esquadrias.models.CurrencyModel;
import dev.luisoliveira.esquadrias.services.CurrencyService;
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
