package com.example.ecommerce.services;

import com.example.ecommerce.api.repositories.ProductRepository;
import com.example.ecommerce.api.repositories.StockRepository;
import com.example.ecommerce.models.DTO.Order;
import com.example.ecommerce.models.MessageSender;
import com.example.ecommerce.models.entities.Stock;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import java.io.Serializable;
import java.util.*;

@Service
public class StockService implements Serializable {

    private StockRepository stockRepository;
    private ProductRepository productRepository;
    private MessageSender messageSender;
    public StockService(StockRepository stockRepository, ProductRepository productRepository, MessageSender messageSender) {
        this.stockRepository = stockRepository;
        this.productRepository = productRepository;
        this.messageSender = messageSender;
    }

    public List<Stock> findAll() { return stockRepository.findAll(); }

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
        return stockRepository.findByProductId(id, Sort.by(Sort.Direction.ASC, "creationDate"));
    }

    public int findNrOfAvailableProductsById(UUID id) {

        int nrOfAvailableProducts = 0;

        List<Stock> stocks = stockRepository.findByProductId(id, Sort.by(Sort.Direction.ASC, "creationDate"));

        //implementation for when we want the number of available products from the oldest stock
        nrOfAvailableProducts = stocks.get(0).getNrUnits();

        return nrOfAvailableProducts;
    }

    public void updateStock(Order order) throws JMSException {

        Map<UUID, Integer> orderList = order.getOrderList();
        int orderQuantity = 0;
        int totalStock = 0;

        for (Map.Entry<UUID, Integer> item : orderList.entrySet()) {
            orderQuantity = item.getValue();
            List<Stock> stocks = stockRepository.findByProductId(item.getKey(), Sort.by(Sort.Direction.ASC, "creationDate"));

            //implementation to check whether total stock can satisfy order demand
            for (Stock stock : stocks) {
                totalStock += stock.getNrUnits();
            }
            if (totalStock < orderQuantity) {
                throw new RuntimeException("Insufficient stock!");
            }

            // implementation to iterate through stock from oldest to newest and decrease from the oldest non-empty one
            for (Stock stock : stocks) {
                if (stock.getNrUnits() == 0) {
                    continue;
                } else if (stock.getNrUnits() < orderQuantity) {
                    orderQuantity -= stock.getNrUnits();
                    stock.setNrUnits(0);
                    continue;
                } else if (stock.getNrUnits() >= orderQuantity) {
                    stock.setNrUnits(stock.getNrUnits() - orderQuantity);
                    orderQuantity = 0;
                    stockRepository.save(stock);
                    break;
                }
            }

        }

        messageSender.sendMessage("The order was created at " + order.getCreationDate().toString() +
                        " and contains the following item list :\n" + order.getOrderList().toString());
        System.out.println("The order was created at " + order.getCreationDate().toString() +
                " and contains the following item list :\n" + order.getOrderList().toString());

        // Kafka alternative stock update notification here
        //kafkaTemplate.send(orderTopic, stock);
        //kafkaTemplate.send(orderTopic, "The order was created at " + order.getCreationDate().toString() +
        //        " and contains the following item list :\n" + order.getOrderList().toString());
    }
}
