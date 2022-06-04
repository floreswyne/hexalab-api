package com.hexalab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class HexalabApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(HexalabApiApplication.class, args);
	}

}
