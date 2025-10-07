package com.iremayvaz.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@ComponentScan(basePackages = {"com.iremayvaz"})
@EntityScan(basePackages = {"com.iremayvaz"})
@EnableJpaRepositories(basePackages = {"com.iremayvaz"})
@EnableAsync
public class StockTrackingSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockTrackingSystemApplication.class, args);
	}

}
