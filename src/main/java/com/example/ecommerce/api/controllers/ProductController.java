package com.example.ecommerce.api.controllers;

import com.example.ecommerce.api.mappers.CategoryMapper;
import com.example.ecommerce.api.mappers.ProductMapper;
import com.example.ecommerce.exceptions.ApiRequestException;
import com.example.ecommerce.models.DTO.CategoryDTO;
import com.example.ecommerce.models.DTO.ProductDTO;
import com.example.ecommerce.models.entities.Category;
import com.example.ecommerce.models.entities.Product;
import com.example.ecommerce.services.CategoryService;
import com.example.ecommerce.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path="/products", produces = "application/json")
@CrossOrigin(origins = "*")
public class ProductController {

    @Autowired
    private ProductService productService;
    private CategoryService categoryService;
    private ProductMapper productMapper;
    private CategoryMapper categoryMapper;

    public ProductController(ProductService productService, CategoryService categoryService, ProductMapper productMapper, CategoryMapper categoryMapper) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.productMapper = productMapper;
        this.categoryMapper = categoryMapper;
    }

    //get a product by id
    @GetMapping("{product-id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable("product-id") String id) {
        Optional<Product> optProduct = productService.findById(UUID.fromString(id));
        if(optProduct.isPresent()) {
            return new ResponseEntity<>(productMapper.toDto(optProduct.get()), HttpStatus.OK);
        }
        else {
            throw new ApiRequestException(ApiRequestException.Exceptions.getDescription(ApiRequestException.Exceptions.ID_NOT_FOUND, id.toString()));
        }

    }

    @GetMapping("categories/{category-id}")
    public ResponseEntity<List<ProductDTO>> getCategoryProductsByCategoryId(@PathVariable("category-id") String id) {

        Optional<Category> optCategory = categoryService.findById(UUID.fromString(id));
        if (!optCategory.isPresent()) {
            throw new ApiRequestException(ApiRequestException.Exceptions.getDescription(ApiRequestException.Exceptions.ID_NOT_FOUND, id.toString()));
        } else {
            // clean repo method names
            List<ProductDTO> products = productService.findByCategories_Id(UUID.fromString(id)).stream()
                    .map(product -> productMapper.toDto(product)).collect(Collectors.toList());

            return new ResponseEntity<>(products, HttpStatus.OK);
        }

    }

    // get all products with a certain name and sort by price
    // leaving the same base URL leads to ambiguous handlers
    @GetMapping("name/{name}")
    public ResponseEntity<List<ProductDTO>> getProductsByNameIsOrderByPriceAsc(@PathVariable("name") String name) {
        List<ProductDTO> products = productService.findAllOrderByPriceAsc(name).stream()
                .map(product -> productMapper.toDto(product)).collect(Collectors.toList());

        if (products.isEmpty()) {
            return new ResponseEntity<>(products, HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    //create a product
    @PostMapping(consumes = "application/json")
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDTO, Errors errors) {

        Product product = productMapper.toProduct(productDTO);
        //ProductDTO productDTO = mapper.toDto(product);
        if (errors.hasFieldErrors()) {
            throw new ApiRequestException(ApiRequestException.Exceptions.getDescription(ApiRequestException.Exceptions.BAD_INPUT));
        }

        productService.save(product);
        return new ResponseEntity<>(productDTO, HttpStatus.CREATED);
    }

    // add a category to a certain product by id
    @PutMapping(value = "{product-id}/categories/{category-id}")
    public ResponseEntity addCategoryToProductById(@PathVariable("product-id") String productId, @PathVariable("category-id") String categoryId) {

        //Category category = categoryMapper.toCategory(categoryDTO);
        //category.setProductCollection();

        // 1) add category to product
        Product product = productService.findById(UUID.fromString(productId)).get();
        Category category = categoryService.findById(UUID.fromString(categoryId)).get();
        product.addCategory(category);
        productService.save(product);

        // 2) add product to category
        category.addProduct(product);
        categoryService.save(category);

        // TO DO: fix this method
        //productService.addCategory(categories, UUID.fromString(productId));

        return new ResponseEntity<>(HttpStatus.OK);
        //ProductDTO productDTO = productMapper.toDto(productService.findById(UUID.fromString(productId)).get());
        //Set<Category> categories = productService.findById(UUID.fromString(productId)).get().getCategories();
    }

    // delete a participant by id
    // DELETE method may be implemented with ResponseEntity
    // DOES NOT WORK FOR CERTAIN UUIDs (UUID string too large)
    @DeleteMapping("{product-id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteProduct (@PathVariable("product-id") String id) {
        try {
            productService.deleteById(UUID.fromString(id));
        } catch (EmptyResultDataAccessException e) {
            throw new ApiRequestException(ApiRequestException.Exceptions.getDescription(ApiRequestException.Exceptions.ID_NOT_FOUND, id.toString()));
        }

    }
}
