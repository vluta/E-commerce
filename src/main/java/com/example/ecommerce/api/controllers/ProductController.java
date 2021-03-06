package com.example.ecommerce.api.controllers;

import com.example.ecommerce.api.mappers.CategoryMapper;
import com.example.ecommerce.api.mappers.ProductMapper;
import com.example.ecommerce.exceptions.ApiRequestException;
import com.example.ecommerce.models.DTO.ProductCreationDTO;
import com.example.ecommerce.models.DTO.ProductDTO;
import com.example.ecommerce.models.entities.Category;
import com.example.ecommerce.models.entities.Product;
import com.example.ecommerce.services.CategoryService;
import com.example.ecommerce.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
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
    public ResponseEntity<ProductDTO> getProductById(@PathVariable("product-id") String id) throws IOException {
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
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductCreationDTO productCreationDTO, Errors errors) {

        //TO DO
        //implement manual validators

        if (errors.hasFieldErrors()) {
            throw new ApiRequestException(ApiRequestException.Exceptions.getDescription(ApiRequestException.Exceptions.BAD_INPUT));
        }

        Product product = productMapper.toProduct(productCreationDTO);

        ProductDTO productDTO = productMapper.toDto(product);
        productService.save(product);
        return new ResponseEntity<>(productDTO, HttpStatus.CREATED);
    }

    // add a category to a certain product by id
    @PutMapping(value = "{product-id}/categories/{category-id}")
    public ResponseEntity addCategoryToProductById(@PathVariable("product-id") String productId, @PathVariable("category-id") String categoryId) {

        //TO DO
        //add exception to validate user id

        // 1) add category to product
        Product product = productService.findById(UUID.fromString(productId)).get();
        Category category = categoryService.findById(UUID.fromString(categoryId)).get();
        product.addCategory(category);
        productService.save(product);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    // delete a speaker-session relationship
    @DeleteMapping("{product-id}/categories/{category-id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteProductCategoryRelation (@PathVariable("product-id") String productId, @PathVariable("category-id") String categoryId) {

        //1) get product and category from db
        Optional<Product> optProduct = productService.findById(UUID.fromString(productId));
        Product product = optProduct.get();
        if(!optProduct.isPresent()) {
            throw new ApiRequestException(ApiRequestException.Exceptions.getDescription(ApiRequestException.Exceptions.ID_NOT_FOUND, productId));
        }
        Optional<Category> optCategory = categoryService.findById(UUID.fromString(categoryId));
        Category category = optCategory.get();
        if(!optCategory.isPresent()) {
            throw new ApiRequestException(ApiRequestException.Exceptions.getDescription(ApiRequestException.Exceptions.ID_NOT_FOUND, categoryId));
        }

        //2) delete old product and category from db
        categoryService.deleteById(UUID.fromString(categoryId));
    }

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
