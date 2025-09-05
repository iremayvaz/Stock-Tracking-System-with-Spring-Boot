package com.iremayvaz.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.iremayvaz"})
@EntityScan(basePackages = {"com.iremayvaz"})
@EnableJpaRepositories(basePackages = {"com.iremayvaz"})
public class StockTrackingSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockTrackingSystemApplication.class, args);
	}

}
