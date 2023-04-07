package com.microservices.customer.services;

import com.microservices.customer.web.model.BeerDto;
import com.microservices.customer.web.model.BeerStyleEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class BeerServiceImpl implements BeerService{

    @Override
    public BeerDto getBeerById(UUID id) {
        return BeerDto.builder()
                .id(UUID.randomUUID())
                .beerName("Old Farmer")
                .beerStyle(BeerStyleEnum.ALE)
                .build();
    }

    @Override
    public BeerDto saveNewBeer(BeerDto beerDto) {
        return BeerDto.builder()
                .id(UUID.randomUUID())
                .build();
    }

    @Override
    public void updateBeer(UUID id, BeerDto beerDto){
        log.debug("Updating a beer...");
    }

    @Override
    public void deleteBeer(UUID id){
        log.debug("Deleting a beer...");
    }
}
