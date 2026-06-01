package com.example.ms_chambilla_registry_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class MsChambillaRegistryServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsChambillaRegistryServerApplication.class, args);
	}

}
