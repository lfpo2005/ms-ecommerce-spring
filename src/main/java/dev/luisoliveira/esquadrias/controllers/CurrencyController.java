package dev.luisoliveira.esquadrias.controllers;

import dev.luisoliveira.esquadrias.models.CurrencyModel;
import dev.luisoliveira.esquadrias.services.CurrencyService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Log4j2
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/currencies")
public class CurrencyController {

    @Autowired
    CurrencyService currencyService;

    @GetMapping("/getCurrency/{currencyEnum}")
    public ResponseEntity<Map<String, CurrencyModel>> getCurrency(@PathVariable String currencyEnum) {
        Map<String, CurrencyModel> currencies = currencyService.getCurrency(currencyEnum);
        return ResponseEntity.ok(currencies);
    }
}
