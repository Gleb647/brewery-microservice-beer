package com.microservices.customer.repositories;

import com.microservices.customer.domain.Beer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BeerRepository extends JpaRepository<Beer, Long> {
    Beer findByBeerName(String beerName);
}
