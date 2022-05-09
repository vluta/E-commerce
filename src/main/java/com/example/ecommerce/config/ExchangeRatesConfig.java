package com.example.ecommerce.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.*;
import java.util.Map;

import okhttp3.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ExchangeRatesConfig {

    private static ObjectMapper mapper = new ObjectMapper();
    private static OkHttpClient client = new OkHttpClient().newBuilder().build();

    //map<String, Float> to match currency to currency rate
    //private Map<String, Float> exchangeRates;

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

        /*Request request = new Request.Builder()
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
        NZD = bodyNode.findValue("NZD").floatValue();*/

        /*exchangeRates.put("USD", bodyNode.findValue("USD").floatValue());
        exchangeRates.put("EUR", bodyNode.findValue("EUR").floatValue());
        exchangeRates.put("JPY", bodyNode.findValue("JPY").floatValue());
        exchangeRates.put("GBP", bodyNode.findValue("GBP").floatValue());
        exchangeRates.put("AUD", bodyNode.findValue("AUD").floatValue());
        exchangeRates.put("CAD", bodyNode.findValue("CAD").floatValue());
        exchangeRates.put("CHF", bodyNode.findValue("CHF").floatValue());
        exchangeRates.put("CNY", bodyNode.findValue("CNY").floatValue());
        exchangeRates.put("SEK", bodyNode.findValue("SEK").floatValue());
        exchangeRates.put("NZD", bodyNode.findValue("NZD").floatValue());*/

        USD = 1.0F;
        EUR = (float) 0.947948;
        JPY = (float) 130.56804;
        GBP = (float) 0.810406;
        AUD = (float) 1.412829;
        CAD = (float) 1.287805;
        CHF = (float) 0.9891;
        CNY = (float) 6.666499;
        SEK = (float) 9.950901;
        NZD = (float) 1.560278;

        /*exchangeRates.put("USD", 1.0F);
        exchangeRates.put("EUR", (float) 0.947948);
        exchangeRates.put("JPY", (float) 130.56804);
        exchangeRates.put("GBP", (float) 0.810406);
        exchangeRates.put("AUD", (float) 1.412829);
        exchangeRates.put("CAD", (float) 1.287805);
        exchangeRates.put("CHF", (float) 0.9891);
        exchangeRates.put("CNY", (float) 6.666499);
        exchangeRates.put("SEK", (float) 9.950901);
        exchangeRates.put("NZD", (float) 1.560278);*/


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

        /*System.out.println(String.valueOf(exchangeRates.get("USD")));
        System.out.println(String.valueOf(exchangeRates.get("EUR")));
        System.out.println(String.valueOf(exchangeRates.get("JPY")));
        System.out.println(String.valueOf(exchangeRates.get("GBP")));
        System.out.println(String.valueOf(exchangeRates.get("AUD")));
        System.out.println(String.valueOf(exchangeRates.get("CAD")));
        System.out.println(String.valueOf(exchangeRates.get("CHF")));
        System.out.println(String.valueOf(exchangeRates.get("CNY")));
        System.out.println(String.valueOf(exchangeRates.get("SEK")));
        System.out.println(String.valueOf(exchangeRates.get("NZD")));*/

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
