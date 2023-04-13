package com.microservices.customer.services;

import com.microservices.customer.domain.Beer;
import com.microservices.customer.web.model.BeerDto;

import java.util.UUID;

public interface BeerService {
    BeerDto getBeerById(Long id);
    BeerDto saveNewBeer(BeerDto beerDto);
    BeerDto updateBeer(Long id, BeerDto beerDto);
    void deleteBeer(Long id);
    Beer findByName(String beerName);
}
