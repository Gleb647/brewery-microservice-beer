package com.microservices.customer.web.controller;

import com.microservices.customer.domain.Beer;
import com.microservices.customer.domain.BeerOrder;
import com.microservices.customer.repositories.BeerRepository;
import com.microservices.customer.services.BeerService;
import com.microservices.customer.web.model.BeerDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@Validated
@RequestMapping("/storage")
@RestController
public class BeerController {

    @Autowired
    private final BeerService beerService;

    public BeerController(BeerService beerService) {
        this.beerService = beerService;
    }


    @GetMapping("/{beerId}")
    public ResponseEntity<BeerDto> getBeerById(@PathVariable("beerId") Long beerId){
        return new ResponseEntity<>(beerService.getBeerById(beerId), HttpStatus.OK);
    }

    @PutMapping("/{beerId}")
    public ResponseEntity<BeerDto> updateBeerById(@PathVariable("beerId") Long beerId, @Valid @RequestBody BeerDto beerDto){
        return new ResponseEntity<>(beerService.updateBeer(beerId, beerDto), HttpStatus.NO_CONTENT);
    }

    @PostMapping("/order-beer")
    public void orderBeer(
            @RequestParam("id") Long id,
            @RequestParam("quantity") Integer quantity,
            @RequestParam("name") String name){
        BeerOrder order = BeerOrder.builder()
                .beerDto(beerService.getBeerById(id))
                .name(name)
                .quantity(quantity)
                .build();
    }
}
