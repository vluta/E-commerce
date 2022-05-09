package com.example.ecommerce.models.DTO;

import com.example.ecommerce.config.ExchangeRatesConfig;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;

import javax.persistence.Column;

@NoArgsConstructor
public class ProductDTO {

    private String name;
    private Double price;
    private String displayPrice;

    //configured value does not bind at runtime
    private String currency;

    private float currencyRate;
    private String description;
    private String pictureUrl;

    public String getCurrency() {
        return currency;
    }

    public float getCurrencyRate() {
        return currencyRate;
    }

    public void setCurrencyRate(String currency) {

        this.currency = currency;

        System.out.println("DTO currency is: " + currency);

        if (currency.equals("USD")) {
            this.currencyRate = ExchangeRatesConfig.USD;
        } else if (currency.equals("EUR")) {
            this.currencyRate = ExchangeRatesConfig.EUR;
        } else if (currency.equals("JPY")) {
            this.currencyRate = ExchangeRatesConfig.JPY;
        } else if (currency.equals("GBP")) {
            this.currencyRate = ExchangeRatesConfig.GBP;
        } else if (currency.equals("AUD")) {
            this.currencyRate = ExchangeRatesConfig.AUD;
        } else if (currency.equals("CAD")) {
            this.currencyRate = ExchangeRatesConfig.CAD;
        } else if (currency.equals("CHF")) {
            this.currencyRate = ExchangeRatesConfig.CHF;
        } else if (currency.equals("CNY")) {
            this.currencyRate = ExchangeRatesConfig.CNY;
        } else if (currency.equals("SEK")) {
            this.currencyRate = ExchangeRatesConfig.SEK;
        } else if (currency.equals("NZD")) {
            this.currencyRate = ExchangeRatesConfig.NZD;
        } else
            throw new RuntimeException("Currency unsupported. Please specify a different configuration currency.");
    }

    public String getDisplayPrice() {
        return displayPrice;
    }

    public void calcDisplayPrice() {
        this.displayPrice = String.valueOf(price.floatValue() * currencyRate + " " + currency);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }
}
