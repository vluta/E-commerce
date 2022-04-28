package com.example.ecommerce.api.mappers;

import com.example.ecommerce.models.DTO.CategoryDTO;
import com.example.ecommerce.models.DTO.ProductDTO;
import com.example.ecommerce.models.entities.Category;
import com.example.ecommerce.models.entities.Product;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    ModelMapper mapper = new ModelMapper();

    public CategoryDTO toDto(Category category) {

        CategoryDTO categoryDTO = mapper.map(category, CategoryDTO.class);

        return categoryDTO;
    }

    public Category toCategory(CategoryDTO categoryDTO) {

        Category category = mapper.map(categoryDTO, Category.class);
        return category;
    }
}
