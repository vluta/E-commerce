package com.example.ecommerce;

import com.example.ecommerce.config.ExchangeRatesConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.IOException;

@SpringBootApplication
@EnableSwagger2
public class ECommerceApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(ECommerceApplication.class, args);

        ExchangeRatesConfig exchangeRatesConfig = new ExchangeRatesConfig();
        exchangeRatesConfig.setExchangeRatesOnDemand();
    }

}
