package dev.calculator.integratins;

import dev.calculator.dtos.resp.CurrencyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "awesome-api", url = "${calculator.awesome-api.url}")
public interface AwesomeApiIntegration {
    @GetMapping("/json/last/{currencyEnum}")
    CurrencyResponse getCurrency(@PathVariable("currencyEnum") String currencyEnum);


}
