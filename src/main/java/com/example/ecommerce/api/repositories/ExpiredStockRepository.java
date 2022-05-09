package com.example.ecommerce.api.repositories;

import com.example.ecommerce.models.entities.ExpiredStock;
import com.example.ecommerce.models.entities.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ExpiredStockRepository extends JpaRepository<ExpiredStock, UUID> {
}
