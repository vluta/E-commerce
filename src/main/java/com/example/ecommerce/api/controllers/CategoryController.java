package com.example.ecommerce.api.controllers;

import com.example.ecommerce.api.mappers.CategoryMapper;
import com.example.ecommerce.exceptions.ApiRequestException;
import com.example.ecommerce.models.DTO.CategoryDTO;
import com.example.ecommerce.models.entities.Category;
import com.example.ecommerce.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path="/categories", produces = "application/json")
@CrossOrigin(origins = "*")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    private CategoryMapper mapper;

    public CategoryController(CategoryService categoryService, CategoryMapper mapper) {
        this.categoryService = categoryService;
        this.mapper = mapper;
    }

    @GetMapping("{product-id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable("product-id") String id) {
        Optional<Category> optCategory = categoryService.findById(UUID.fromString(id));
        if(optCategory.isPresent()) {
            return new ResponseEntity<>(mapper.toDto(optCategory.get()), HttpStatus.OK);
        }
        else {
            throw new ApiRequestException(ApiRequestException.Exceptions.getDescription(ApiRequestException.Exceptions.ID_NOT_FOUND, id.toString()));
        }

    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO, Errors errors) {

        Category category = mapper.toCategory(categoryDTO);
        //ProductDTO productDTO = mapper.toDto(product);
        if (errors.hasFieldErrors()) {
            throw new ApiRequestException(ApiRequestException.Exceptions.getDescription(ApiRequestException.Exceptions.BAD_INPUT));
        }

        categoryService.save(category);
        return new ResponseEntity<>(categoryDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("{category-id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteCategory (@PathVariable("category-id") String id) {
        try {
            categoryService.deleteById(UUID.fromString(id));
        } catch (EmptyResultDataAccessException e) {
            throw new ApiRequestException(ApiRequestException.Exceptions.getDescription(ApiRequestException.Exceptions.ID_NOT_FOUND, id.toString()));
        }

    }

}
