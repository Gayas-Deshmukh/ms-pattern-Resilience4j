package com.weshopify.platform.features.brands.outbound;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CategoriesOutboundService {

	@Value("${service.category.baseUri}")
	private String categoryServiceUri;
	
	@Autowired
	private RestTemplate restTemplate;
	
	public String findCategoryById(int id) 
	{
		categoryServiceUri = categoryServiceUri+"?sortDir=asc";
		log.info("Category service URL is:\t"+categoryServiceUri);
		
		ResponseEntity<String> response = restTemplate.getForEntity(categoryServiceUri, String.class);
		log.info("response code is:\t"+response.getStatusCode());
		
		if(response.getStatusCodeValue()==HttpStatus.OK.value())
		{
			String respData = response.getBody();
			return respData;
		}
		else 
		{
			throw new RuntimeException("No Category Found with the Category Id:\t"+id);
		}
	}
}
