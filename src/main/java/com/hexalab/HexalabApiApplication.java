package com.hexalab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class HexalabApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(HexalabApiApplication.class, args);
	}

}
