package com.example.ecommerce.api.controllers;

import com.example.ecommerce.config.ExchangeRatesConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping(path="/currencies", produces = "application/json")
@CrossOrigin(origins = "*")
public class CurrencyController {

    @Autowired
    private ExchangeRatesConfig exchangeRatesConfig;

    public CurrencyController(ExchangeRatesConfig exchangeRatesConfig) {
        this.exchangeRatesConfig = exchangeRatesConfig;
    }

    @GetMapping("/exchange")
    public ResponseEntity getLatestCurrencyExchange() throws IOException {
        exchangeRatesConfig.setExchangeRatesOnDemand();
        return new ResponseEntity(HttpStatus.OK);
    }
}
