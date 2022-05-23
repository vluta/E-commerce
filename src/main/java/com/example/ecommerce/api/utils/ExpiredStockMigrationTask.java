package com.example.ecommerce.api.utils;

import com.example.ecommerce.api.mappers.ExpiredStockMapper;
import com.example.ecommerce.api.repositories.ExpiredStockRepository;
import com.example.ecommerce.models.entities.Stock;
import com.example.ecommerce.services.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
public class ExpiredStockMigrationTask {

    @Autowired
    private StockService stockService;
    @Autowired
    private ExpiredStockRepository expiredStockRepository;
    @Autowired
    private ExpiredStockMapper expiredStockMapper;

    @Scheduled(cron = "* * 0 * * ? *")
    //@Scheduled(cron = "${cron.expiration}")
    public void migrateExpiredStockTask() throws IOException {
        migrateExpiredStockOnDemand();
    }

    public void migrateExpiredStockOnDemand() {

        List<Stock> stocks = stockService.findAll();

        for (Stock stock : stocks) {

            if (stock.getExpDate().isBefore(LocalDate.now())) {

                // 1) migrate expired stock to expired stocks table
                expiredStockRepository.save(expiredStockMapper.toExpiredStock(stock));

                // 2) delete expired stock from non-expired stocks table
                stockService.deleteById(stock.getId());
            }
        }
    }
}
