package com.example.ecommerce.api.mappers;

import com.example.ecommerce.models.DTO.ProductDTO;
import com.example.ecommerce.models.entities.Product;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    ModelMapper mapper = new ModelMapper();

    public ProductDTO toDto(Product product) {

        ProductDTO productDTO = mapper.map(product, ProductDTO.class);
        productDTO.setDisplayPrice();
        return productDTO;
    }

    public Product toProduct(ProductDTO productDTO) {

        Product product = mapper.map(productDTO, Product.class);
        return product;
    }
}
