package com.microservices.customer.domain;

import com.microservices.customer.web.model.BeerDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BeerOrder {
    private BeerDto beerDto;
    private String name;
    private Integer quantity;
}
