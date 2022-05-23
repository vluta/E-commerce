package com.example.ecommerce.services;

import com.example.ecommerce.api.repositories.CategoryRepository;
import com.example.ecommerce.models.entities.Category;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Optional<Category> findById(UUID id) {
        return categoryRepository.findById(id);
    }

    public void save(Category category) {
        Objects.requireNonNull(category);
        categoryRepository.save(category);
    }

    public void deleteById(UUID id) {
        categoryRepository.deleteById(id);
    }

}
