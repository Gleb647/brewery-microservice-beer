package com.microservices.customer.services;

import com.microservices.customer.domain.Beer;
import com.microservices.customer.repositories.BeerRepository;
import com.microservices.customer.web.controller.NotFoundException;
import com.microservices.customer.web.mappers.BeerMapper;
import com.microservices.customer.web.mappers.BeerMapperImpl;
import com.microservices.customer.web.model.BeerDto;
import com.microservices.customer.web.model.BeerStyleEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.processing.Generated;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
@Generated(
        value = "org.mapstruct.ap.MappingProcessor"
)
public class BeerServiceImpl implements BeerService{
    private final BeerRepository beerRepository;
    private final BeerMapperImpl beerMapper;

    @Override
    public BeerDto getBeerById(UUID id) {
        return beerMapper.beerToBeerDto(beerRepository.findById(id).orElseThrow(NotFoundException::new));
    }

    @Override
    public BeerDto saveNewBeer(BeerDto beerDto) {
        return beerMapper.beerToBeerDto(beerRepository.save(beerMapper.beerDtoToBeer(beerDto)));
    }

    @Override
    public BeerDto updateBeer(UUID id, BeerDto beerDto){
        Beer beer = beerRepository.findById(id).orElseThrow(NotFoundException::new);
        beer.setBeerName(beerDto.getBeerName());
        beer.setBeerStyle(beerDto.getBeerStyle().name());
        beer.setPrice(beerDto.getPrice());
        beer.setUpc(beerDto.getUpc());
        return beerMapper.beerToBeerDto(beerRepository.save(beer));
    }

    @Override
    public void deleteBeer(UUID id){
        log.debug("Deleting a beer...");
    }
}
