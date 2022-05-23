package com.example.ecommerce.api.repositories;

import com.example.ecommerce.models.entities.ExpiredStock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ExpiredStockRepository extends JpaRepository<ExpiredStock, UUID> {
}
