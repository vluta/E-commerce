package com.example.ecommerce.models.DTO;

import lombok.NoArgsConstructor;

import javax.persistence.Column;

@NoArgsConstructor
public class ProductDTO {

    private String name;
    private Double price;
    //private Double quantAvailable;
    private String description;
    private String pictureUrl;

    /*public Double getQuantAvailable() {
        return quantAvailable;
    }

    public void setQuantAvailable(Double quantAvailable) {
        this.quantAvailable = quantAvailable;
    }*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }
}
