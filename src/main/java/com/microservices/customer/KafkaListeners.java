package com.microservices.customer;

import com.jayway.jsonpath.JsonPath;
import com.microservices.customer.domain.Beer;
import com.microservices.customer.services.BeerService;
import com.microservices.customer.web.mappers.BeerMapperImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaListeners {

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final BeerMapperImpl beerMapper;

    private final BeerService beerService;

    public KafkaListeners(KafkaTemplate<String, String> kafkaTemplate, BeerMapperImpl beerMapper, BeerService beerService) {
        this.kafkaTemplate = kafkaTemplate;
        this.beerMapper = beerMapper;
        this.beerService = beerService;
    }

    @KafkaListener(topics = "Orders", groupId = "groupId")
    void listener(String data){
        String beerName = JsonPath.read(data, "$.beerName");
        int quantity = JsonPath.read(data, "$.quantity");
        String clientName = JsonPath.read(data, "$.name");
        Beer beer = beerService.findByName(beerName);
        if (beer == null){
            log.warn("We do not have such beer: " + beerName);
            return;
        }
        if (beer.getQuantityOnHand() >= quantity){
            log.info("New order made: " + clientName + " " + beerName + " " + quantity);
            beer.setQuantityOnHand(beer.getQuantityOnHand()-quantity);
            kafkaTemplate.send("Approve", data);
        }else{
            log.warn("The beer "  + beerName + " is over");
            kafkaTemplate.send("Produce", data);
        }
    }
}
