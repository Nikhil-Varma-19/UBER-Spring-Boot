package com.example.uber.uber_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class UberAplication {

	public static void main(String[] args) {
		SpringApplication.run(UberAplication.class, args);
	}

}
