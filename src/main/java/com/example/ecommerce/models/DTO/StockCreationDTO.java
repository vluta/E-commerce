package com.example.ecommerce.models.DTO;

import com.example.ecommerce.models.entities.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class StockCreationDTO {

    private int nrUnits;
    //private LocalDate creationDate;
    private LocalDate expDate;
   // private Product product;
}
