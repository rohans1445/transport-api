package com.example.transportapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TransportApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransportApiApplication.class, args);
	}

}
