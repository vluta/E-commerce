package com.example.ecommerce.models.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public class Order {

    private Map<UUID, Integer> orderList;
    private LocalDate creationDate;
}
