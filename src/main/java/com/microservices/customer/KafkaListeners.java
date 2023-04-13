package com.microservices.customer;

import com.jayway.jsonpath.JsonPath;
import com.microservices.customer.domain.Beer;
import com.microservices.customer.domain.BeerOrder;
import com.microservices.customer.services.BeerService;
import com.microservices.customer.web.mappers.BeerMapperImpl;
import com.microservices.customer.web.model.BeerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {

    private final KafkaTemplate<String, BeerDto> kafkaTemplate;

    private final BeerMapperImpl beerMapper;

    private final BeerService beerService;

    public KafkaListeners(KafkaTemplate<String, BeerDto> kafkaTemplate, BeerMapperImpl beerMapper, BeerService beerService) {
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
            System.out.println("We do not have such beer: " + beerName);
            return;
        }
        if (beer.getQuantityOnHand() > quantity){
            System.out.println("New order made: " + clientName + " " + beerName + " " + quantity);
            BeerDto beerDto = beerMapper.beerToBeerDto(beer);
            kafkaTemplate.send("Approve", beerDto);
        }else{
            System.out.println("The beer "  + beerName + " is over");
        }
    }
}
