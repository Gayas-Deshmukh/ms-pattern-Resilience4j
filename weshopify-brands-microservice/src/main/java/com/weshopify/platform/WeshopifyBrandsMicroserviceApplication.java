package com.weshopify.platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableFeignClients
public class WeshopifyBrandsMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeshopifyBrandsMicroserviceApplication.class, args);
	}
	
	@Bean
	public RestTemplate getrestTemplate()
	{
		return new RestTemplate();
	}

}
