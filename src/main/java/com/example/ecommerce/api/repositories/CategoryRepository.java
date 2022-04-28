package com.example.ecommerce.api.repositories;

import com.example.ecommerce.models.entities.Category;
import com.example.ecommerce.models.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {

    @Override
    Optional<Category> findById(UUID uuid);

    @Override
    void deleteById(UUID uuid);
}
