package com.example.ecommerce.api.controllers;

import com.example.ecommerce.config.ExchangeRatesConfig;
import com.example.ecommerce.exceptions.ApiRequestException;
import com.example.ecommerce.models.DTO.StockDTO;
import com.example.ecommerce.models.entities.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
