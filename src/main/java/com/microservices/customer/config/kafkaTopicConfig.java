package com.microservices.customer.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class kafkaTopicConfig {

    @Bean
    public NewTopic beerTopic(){
        return TopicBuilder.name("beer")
                .build();
    }
}
