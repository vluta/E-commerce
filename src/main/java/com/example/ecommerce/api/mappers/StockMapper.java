package com.example.ecommerce.api.mappers;

import com.example.ecommerce.models.DTO.StockCreationDTO;
import com.example.ecommerce.models.DTO.StockDTO;
import com.example.ecommerce.models.entities.Stock;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class StockMapper {

    private ModelMapper mapper = new ModelMapper();

    public StockDTO toDto(Stock stock) {

        StockDTO stockDTO = mapper.map(stock, StockDTO.class);
        return stockDTO;
    }

    public Stock toStock(StockCreationDTO stockCreationDTO) {

        Stock stock = mapper.map(stockCreationDTO, Stock.class);
        return stock;
    }

}
