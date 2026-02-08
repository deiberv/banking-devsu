package com.devsu.ing.deiberv.ms.cliente;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class MsClientePersonaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsClientePersonaApplication.class, args);
	}

}
