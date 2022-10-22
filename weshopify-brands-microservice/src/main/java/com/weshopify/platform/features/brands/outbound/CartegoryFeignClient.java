package com.weshopify.platform.features.brands.outbound;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/*
@FeignClient(url ="${service.category.baseUri}", name="weshopify-categories-service")
public interface CartegoryFeignClient 
{
	@GetMapping(value = "/{id}")
	public ResponseEntity<String> findCategoryById(@PathVariable("id") int id);
}
*/


@FeignClient(name ="we-shopify-catogories-microservice") // we can give service name in upper case or lower case
public interface CartegoryFeignClient 
{
	@GetMapping(value = "categories/{id}")
	public ResponseEntity<String> findCategoryById(@PathVariable("id") int id);
}
