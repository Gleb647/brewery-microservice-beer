package com.microservices.customer;

import com.microservices.customer.domain.Beer;
import com.microservices.customer.repositories.BeerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;

import java.math.BigDecimal;

@SpringBootApplication
public class CustomerApplication {
	private final BeerRepository beerRepository;

	public CustomerApplication(BeerRepository beerRepository) {
		this.beerRepository = beerRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(CustomerApplication.class, args);
	}

	@Bean
	CommandLineRunner run(){
		if (beerRepository.count() == 0) {
			return args -> {
				beerRepository.save(Beer.builder()
						.beerName("Mango Bobs")
						.beerStyle("IPA")
						.quantityOnHand(0)
						.upc(3701000001L)
						.price(new BigDecimal("12.95"))
						.build());

				beerRepository.save(Beer.builder()
						.beerName("Retro Boomer")
						.beerStyle("IPA")
						.quantityOnHand(2)
						.upc(3701000002L)
						.price(new BigDecimal("12.95"))
						.build());

				beerRepository.save(Beer.builder()
						.beerName("Galaxy Pink")
						.beerStyle("PALE_ALE")
						.quantityOnHand(100)
						.upc(3701000003L)
						.price(new BigDecimal("10.95"))
						.build());

				beerRepository.save(Beer.builder()
						.beerName("Miller Lite")
						.beerStyle("PALE_ALE")
						.quantityOnHand(4)
						.upc(3701000004L)
						.price(new BigDecimal("8.95"))
						.build());
			};
		}
		return null;
	}

}
