package dev.luisoliveira.msshoppingcart.model.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/status")
public class StatusApi {

    @GetMapping
    public ResponseEntity<HashMap<String, Object>> getApiStatus (){
        var response = new HashMap<String, Object>();
        response.put("Status", HttpStatus.OK.value());
        response.put("Message ", "Back-end - ms-product-management");
        return ResponseEntity.ok(response);
    }

}
