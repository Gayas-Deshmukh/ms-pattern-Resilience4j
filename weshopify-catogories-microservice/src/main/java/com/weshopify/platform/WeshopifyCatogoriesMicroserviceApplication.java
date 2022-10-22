package com.weshopify.platform;

import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.json.JacksonSerializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
public class WeshopifyCatogoriesMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeshopifyCatogoriesMicroserviceApplication.class, args);
	}
}
