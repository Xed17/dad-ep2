package com.example.ms_gestion_taller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsGestionTallerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsGestionTallerApplication.class, args);
	}

}
