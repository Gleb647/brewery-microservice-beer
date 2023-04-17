package com.microservices.customer.domain;

import com.microservices.customer.web.model.BeerDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BeerOrder {

    private String id;
    private String clientName;
    private Map<String, Integer> orderedBeerMap;
}
