package com.microservices.customer.services;

import com.microservices.customer.web.model.BeerDto;

import java.util.UUID;

public interface BeerService {
    BeerDto getBeerById(UUID id);
    BeerDto saveNewBeer(BeerDto beerDto);
    void updateBeer(UUID id, BeerDto beerDto);
    void deleteBeer(UUID id);
}
