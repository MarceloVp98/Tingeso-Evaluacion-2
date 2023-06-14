package com.tingesoevaluacion2.acopioservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class AcopioServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AcopioServiceApplication.class, args);
	}

}
