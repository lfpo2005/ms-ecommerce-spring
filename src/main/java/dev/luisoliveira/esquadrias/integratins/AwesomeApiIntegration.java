package dev.luisoliveira.esquadrias.integratins;

import dev.luisoliveira.esquadrias.dtos.resp.CurrencyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "awesome-api", url = "${esquadrias.awesome-api.url}")
public interface AwesomeApiIntegration {
    @GetMapping("/json/last/{currencyEnum}")
    CurrencyResponse getCurrency(@PathVariable("currencyEnum") String currencyEnum);


}
