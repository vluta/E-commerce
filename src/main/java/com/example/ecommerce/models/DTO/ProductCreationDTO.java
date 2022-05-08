package com.example.ecommerce.models.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

@NoArgsConstructor
@Getter
@Setter
public class ProductCreationDTO {

    private String name;
    private Double price;
    private String description;
    private String pictureUrl;

}
