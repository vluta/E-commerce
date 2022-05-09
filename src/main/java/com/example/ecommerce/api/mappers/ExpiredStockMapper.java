package com.example.ecommerce.api.mappers;

import com.example.ecommerce.models.entities.ExpiredStock;
import com.example.ecommerce.models.entities.Stock;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ExpiredStockMapper {

    private ModelMapper mapper = new ModelMapper();

    public ExpiredStock toExpiredStock(Stock stock) {

        ExpiredStock expiredStock = mapper.map(stock, ExpiredStock.class);
        return expiredStock;
    }
}
