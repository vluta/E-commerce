package com.example.ecommerce.models.DTO;

import com.example.ecommerce.config.ExchangeRatesConfig;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;

import javax.persistence.Column;

@NoArgsConstructor
public class ProductDTO {

    private String name;
    private Double price;
    private String displayPrice;

    @Value("${currency.default}")
    private String currency;

    private float currencyRate;

    private String description;
    private String pictureUrl;

    //private Double quantAvailable;

    /*public Double getQuantAvailable() {
        return quantAvailable;
    }

    public void setQuantAvailable(Double quantAvailable) {
        this.quantAvailable = quantAvailable;
    }*/

    public String getCurrency() {
        return currency;
    }

    public float getCurrencyRate() {
        return currencyRate;
    }

    public void setCurrencyRate() {

        /*public static final String USD = "USD";
        public static final String EUR = "EUR";
        public static final String JPY = "JPY";
        public static final String GBP = "GBP";
        public static final String AUD = "AUD";
        public static final String CAD = "CAD";
        public static final String CHF = "CHF";
        public static final String CNY = "CNY";
        public static final String SEK = "SEK";
        public static final String NZD = "NZD";*/

        switch (currency) {
            case "USD":
                this.currencyRate = ExchangeRatesConfig.USD;
            case "EUR":
                this.currencyRate = ExchangeRatesConfig.EUR;
            case "JPY":
                this.currencyRate = ExchangeRatesConfig.JPY;
            case "GBP":
                this.currencyRate = ExchangeRatesConfig.GBP;
            case "AUD":
                this.currencyRate = ExchangeRatesConfig.AUD;
            case "CAD":
                this.currencyRate = ExchangeRatesConfig.CAD;
            case "CHF":
                this.currencyRate = ExchangeRatesConfig.CHF;
            case "CNY":
                this.currencyRate = ExchangeRatesConfig.CNY;
            case "SEK":
                this.currencyRate = ExchangeRatesConfig.SEK;
            case "NZD":
                this.currencyRate = ExchangeRatesConfig.NZD;
            default:
                throw new RuntimeException("Currency unsupported. Please specify a different configuration currency.");
        }
    }

    public String getDisplayPrice() {
        return displayPrice;
    }

    public void setDisplayPrice() {
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
