package com.microservices.customer;

import com.microservices.customer.domain.Beer;
import com.microservices.customer.repositories.BeerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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
						.beerName("Mango bobs")
						.beerStyle("IPA")
						.quantityToBrew(200)
						.minOnHand(12)
						.upc(3701000001L)
						.price(new BigDecimal("12.95"))
						.build());

				beerRepository.save(Beer.builder()
						.beerName("Galaxy Cat")
						.beerStyle("Pale_ALE")
						.quantityToBrew(200)
						.minOnHand(12)
						.upc(3701000002L)
						.price(new BigDecimal("11.95"))
						.build());
			};
		}
		return null;
	}
}
