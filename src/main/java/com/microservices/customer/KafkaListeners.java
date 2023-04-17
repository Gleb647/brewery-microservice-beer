package com.microservices.customer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.jayway.jsonpath.JsonPath;
import com.microservices.customer.domain.Beer;
import com.microservices.customer.domain.BeerOrder;
import com.microservices.customer.services.BeerService;
import com.microservices.customer.web.mappers.BeerMapperImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

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
    void listener(String data) throws JsonProcessingException {
        String clientName = JsonPath.read(data, "$.clientName");
        Map<String, Integer> order = new HashMap<>();
        order = JsonPath.read(data, "$.orderedBeerMap");
        for (Map.Entry<String, Integer> entry: order.entrySet()){
            Beer beer = beerService.findByName(entry.getKey());
            if (beer == null){
                log.warn("We do not have such beer: " + entry.getKey());
                return;
            }
            Map<String, Integer> orderEntry = new HashMap<>();
            orderEntry.put(entry.getKey(), entry.getValue());
            BeerOrder send = BeerOrder.builder()
                    .clientName(clientName)
                    .orderedBeerMap(orderEntry)
                    .build();
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = ow.writeValueAsString(send);
            if (beer.getQuantityOnHand() >= entry.getValue()){
                log.info("New order made: " + clientName + " " + entry.getKey() + " " + entry.getValue());
                beer.setQuantityOnHand(beer.getQuantityOnHand()-entry.getValue());
                kafkaTemplate.send("Approve", json);
            }else{
                log.warn("The beer "  + entry.getKey() + " is over");
                kafkaTemplate.send("Produce", json);
            }
        }
    }
}
