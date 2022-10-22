package com.weshopify.platform.features.categories.handlers;

import org.axonframework.eventsourcing.EventSourcingHandler;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.weshopify.platform.features.categories.event.CategoryDomainEvent;
import com.weshopify.platform.features.categories.service.CategoryService;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class CategoryDomainEventPublishHandler 
{
	@Value("${spring.application.name}")
	private String qname;
	
	@Value("${spring.rabbitmq.template.routing-key}")
	private String routingKey;
	
	@Value("${spring.rabbitmq.template.exchange}")
	private String exchange;
	 	
	@Autowired
	private RabbitTemplate rabbitMqTemplate;
	
	//@EventSourcingHandler
	public void onCategoryupdate(CategoryDomainEvent updateCategoryEvent)
	{
		log.info("Updated Category is ready for publishing:\t" + updateCategoryEvent);
	//	log.info("Queue Name  :"+ this.qname);
		
		 
		// log.info("quename is:\t"+ this.rabbitMqTemplate.getExchange());
		// log.info("routing key is:\t"+ this.rabbitMqTemplate.getRoutingKey());
		 log.info("quename is:\t"+ this.qname);
		 log.info("routing key is:\t"+ this.routingKey);
		 log.info("exchange key is:\t"+ this.exchange);
		 
	}
}
