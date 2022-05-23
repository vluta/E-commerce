package com.example.ecommerce.models.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
