package com.example.ecommerce.services;

import com.example.ecommerce.api.repositories.ProductRepository;
import com.example.ecommerce.api.repositories.StockRepository;
import com.example.ecommerce.models.DTO.Order;
import com.example.ecommerce.models.entities.Product;
import com.example.ecommerce.models.entities.Stock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;

@Service
public class StockService implements Serializable {

    private StockRepository stockRepository;
    private ProductRepository productRepository;

    public Optional<Stock> findById(UUID id) {
        return stockRepository.findById(id);
    }

    public void save(Stock stock) {
        Objects.requireNonNull(stock);
        stockRepository.save(stock);
    }

    public void deleteById(UUID id) {
        stockRepository.deleteById(id);
    }

    public List<Stock> findByProduct_IdOrderByCreationDateAsc(UUID id) {

        //return productRepository.findAll(PageRequest.of(0, 3, Sort.by(Sort.Direction.ASC, "price")));
        return stockRepository.findByProduct_IdOrderByCreationDateAsc(id, Sort.by(Sort.Direction.ASC));
    }

    public int findNrOfAvailableProductsById(UUID id) {

        int nrOfAvailableProducts = 0;

        List<Stock> stocks = stockRepository.findByProduct_IdOrderByCreationDateAsc(id, Sort.by(Sort.Direction.ASC));

        // implementation for when we want the total number of available products summed up from all stocks of that product
        //for (Stock stock : stocks) {
        //    nrOfAvailableProducts += stock.getNrUnits();
        //}

        //implementation for when we want the number of available products from the oldest stock
        nrOfAvailableProducts = stocks.get(0).getNrUnits();

        return nrOfAvailableProducts;
    }

    public void updateStock(Order order) {

        Map<UUID, Integer> orderList = order.getOrderList();
        Stock stock;

        for (Map.Entry<UUID, Integer> item : orderList.entrySet()) {
            List<Stock> stocks = stockRepository.findByProduct_IdOrderByCreationDateAsc(item.getKey(), Sort.by(Sort.Direction.ASC));
            stock = stocks.get(0);
            stock.setNrUnits(stock.getNrUnits() - item.getValue());
            stockRepository.save(stock);
        }
    }
}
