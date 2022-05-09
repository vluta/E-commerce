package com.example.ecommerce.api.mappers;

import com.example.ecommerce.models.DTO.ProductCreationDTO;
import com.example.ecommerce.models.DTO.ProductDTO;
import com.example.ecommerce.models.entities.Product;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    @Value("${currency.default}")
    private String configCurrency;

    private ModelMapper mapper = new ModelMapper();

    public ProductDTO toDto(Product product) {

        ProductDTO productDTO = mapper.map(product, ProductDTO.class);
        productDTO.setCurrencyRate(configCurrency);
        productDTO.calcDisplayPrice();
        return productDTO;
    }

    public Product toProduct(ProductCreationDTO productCreationDTO) {

        Product product = mapper.map(productCreationDTO, Product.class);
        return product;
    }
}
