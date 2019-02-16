package com.groupay.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class GrouPayAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(GrouPayAppApplication.class, args);
	}

}
