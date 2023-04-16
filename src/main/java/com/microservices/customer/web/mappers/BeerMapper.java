package com.microservices.customer.web.mappers;

import com.microservices.customer.domain.Beer;
import com.microservices.customer.web.model.BeerDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BeerMapper {
    Beer beerDtoToBeer(BeerDto dto);
    BeerDto beerToBeerDto(Beer beer);
}
