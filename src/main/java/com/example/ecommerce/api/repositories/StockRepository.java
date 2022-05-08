package com.example.ecommerce.api.repositories;

import com.example.ecommerce.models.entities.Stock;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StockRepository extends JpaRepository<Stock, UUID> {

    @Override
    List<Stock> findAll();

    @Override
    Optional<Stock> findById(UUID uuid);

    List<Stock> findByProduct_IdOrderByCreationDateAsc(UUID id, Sort sort);

    @Override
    void deleteById(UUID uuid);
}
