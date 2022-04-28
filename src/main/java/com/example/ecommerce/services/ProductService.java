package com.example.ecommerce.services;

import com.example.ecommerce.api.repositories.ProductRepository;
import com.example.ecommerce.models.entities.Category;
import com.example.ecommerce.models.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;

@Service
public class ProductService implements Serializable {

    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Optional<Product> findById(UUID id) {
        return productRepository.findById(id);
    }

    public void save(Product product) {
        Objects.requireNonNull(product);
        productRepository.save(product);
    }

    public List<Product> findByCategories_Id(UUID id) {
        return productRepository.findByCategories_Id(id);
    }

    public void deleteById(UUID id) {
        productRepository.deleteById(id);
    }

    public Page<Product> findAllOrderByPriceAsc(String name) {

        return productRepository.findAll(PageRequest.of(0, 3, Sort.by(Sort.Direction.ASC, "price")));
    }

    public void addCategory(Category categories, UUID id) {
        Objects.requireNonNull(categories);
        productRepository.addCategory(categories, id);
    }
}
