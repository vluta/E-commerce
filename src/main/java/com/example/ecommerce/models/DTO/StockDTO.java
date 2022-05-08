package com.example.ecommerce.models.DTO;

import com.example.ecommerce.models.entities.Product;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

public class StockDTO {

    private int nrUnits;
    private LocalDate creationDate;
    private LocalDate expDate;
    private Product product;

    public int getNrUnits() {
        return nrUnits;
    }

    public void setNrUnits(int nrUnits) {
        this.nrUnits = nrUnits;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDate getExpDate() {
        return expDate;
    }

    public void setExpDate(LocalDate expDate) {
        this.expDate = expDate;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
