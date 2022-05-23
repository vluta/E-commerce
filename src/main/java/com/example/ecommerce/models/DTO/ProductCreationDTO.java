package com.example.ecommerce.models.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ProductCreationDTO {

    private String name;
    private Double price;
    private String description;
    private String pictureUrl;

}
