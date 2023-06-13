package com.tingesoevaluacion2.proveedorservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ProveedorServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProveedorServiceApplication.class, args);
	}

}
