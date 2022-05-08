package com.example.ecommerce.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.*;
import okhttp3.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ExchangeRatesConfig {

    private static ObjectMapper mapper = new ObjectMapper();
    private static OkHttpClient client = new OkHttpClient().newBuilder().build();

    public static float USD;
    public static float EUR;
    public static float JPY;
    public static float GBP;
    public static float AUD;
    public static float CAD;
    public static float CHF;
    public static float CNY;
    public static float SEK;
    public static float NZD;

    public void setExchangeRatesOnDemand() throws IOException {

        Request request = new Request.Builder()
                .url("https://api.apilayer.com/exchangerates_data/latest?symbols=USD%2CEUR%2CJPY%2CGBP%2CAUD%2CCAD%2CCHF%2CCNY%2CSEK%2CNZD&base=USD")
                .addHeader("apikey", "isxvUb2O1UEIy74KrAggi2L2qWz3ZRNj")
                .build();
        Response response = client.newCall(request).execute();
        //System.out.println(response.body().string());

        String body = response.body().string();
        JsonNode bodyNode = mapper.readTree(body);

        USD = bodyNode.findValue("USD").floatValue();
        EUR = bodyNode.findValue("EUR").floatValue();
        JPY = bodyNode.findValue("JPY").floatValue();
        GBP = bodyNode.findValue("GBP").floatValue();
        AUD = bodyNode.findValue("AUD").floatValue();
        CAD = bodyNode.findValue("CAD").floatValue();
        CHF = bodyNode.findValue("CHF").floatValue();
        CNY = bodyNode.findValue("CNY").floatValue();
        SEK = bodyNode.findValue("SEK").floatValue();
        NZD = bodyNode.findValue("NZD").floatValue();

        System.out.println(String.valueOf(USD));
        System.out.println(String.valueOf(EUR));
        System.out.println(String.valueOf(JPY));
        System.out.println(String.valueOf(GBP));
        System.out.println(String.valueOf(AUD));
        System.out.println(String.valueOf(CAD));
        System.out.println(String.valueOf(CHF));
        System.out.println(String.valueOf(CNY));
        System.out.println(String.valueOf(SEK));
        System.out.println(String.valueOf(NZD));

        /*{
            "base": "USD",
            "date": "2021-03-17",
            "rates": {
                    "EUR": 0.813399,
                    "GBP": 0.72007,
                    "JPY": 107.346001
            },
            "success": true,
            "timestamp": 1519296206
        }*/
    }

    @Scheduled(cron = "${cron.expression}")
    public void setExchangeRatesTask() throws IOException {
        setExchangeRatesOnDemand();
    }
}
