package com.compassouol.springbootcompasso.mserviceclientes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class MserviceClientesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MserviceClientesApplication.class, args);
	}

}
