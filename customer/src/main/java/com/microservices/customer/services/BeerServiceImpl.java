package com.microservices.customer.services;

import com.microservices.customer.domain.Beer;
import com.microservices.customer.repositories.BeerRepository;
import com.microservices.customer.web.controller.NotFoundException;
import com.microservices.customer.web.mappers.BeerMapper;
import com.microservices.customer.web.model.BeerDto;
import com.microservices.customer.web.model.BeerStyleEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class BeerServiceImpl implements BeerService{
    private final BeerRepository beerRepository;
    private final BeerMapper beerMaper;

    @Override
    public BeerDto getBeerById(UUID id) {
        return beerMaper.beerToBeerDto(beerRepository.findById(id).orElseThrow(NotFoundException::new));
    }

    @Override
    public BeerDto saveNewBeer(BeerDto beerDto) {
        return beerMaper.beerToBeerDto(beerRepository.save(beerMaper.beerDtoToBeer(beerDto)));
    }

    @Override
    public BeerDto updateBeer(UUID id, BeerDto beerDto){
        Beer beer = beerRepository.findById(id).orElseThrow(NotFoundException::new);
        beer.setBeerName(beerDto.getBeerName());
        beer.setBeerStyle(beerDto.getBeerStyle().name());
        beer.setPrice(beerDto.getPrice());
        beer.setUpc(beerDto.getUpc());
        return beerMaper.beerToBeerDto(beerRepository.save(beer));
    }

    @Override
    public void deleteBeer(UUID id){
        log.debug("Deleting a beer...");
    }
}
