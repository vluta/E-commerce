package com.example.ecommerce.models.DTO;

import java.time.LocalDate;

public class StockDTO {

    private int nrUnits;
    private LocalDate creationDate;
    private LocalDate expDate;

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
}
