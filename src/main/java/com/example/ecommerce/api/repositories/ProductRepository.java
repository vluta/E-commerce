package com.example.ecommerce.api.repositories;

import com.example.ecommerce.models.entities.Category;
import com.example.ecommerce.models.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {

    @Override
    Optional<Product> findById(UUID uuid);

    @Override
    void deleteById(UUID uuid);

    List<Product> findByCategories_Id(UUID id);
}
