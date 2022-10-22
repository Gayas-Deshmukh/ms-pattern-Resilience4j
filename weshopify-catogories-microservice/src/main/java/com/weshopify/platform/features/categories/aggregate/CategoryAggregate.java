package com.weshopify.platform.features.categories.aggregate;

import java.io.Serializable;
import java.util.UUID;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.weshopify.platform.features.categories.command.CategoryCommand;
import com.weshopify.platform.features.categories.event.CategoryDomainEvent;
import com.weshopify.platform.features.categories.handlers.CategoryDomainEventPublishHandler;

import lombok.extern.slf4j.Slf4j;

// it Contains the both CommandHandler & Aggregate Event Handler

@Aggregate
@Slf4j
public class CategoryAggregate  implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6357082126439114731L;

	@AggregateIdentifier
	private String id;

	private String name;
	
	private String alias;
	
	private String image;
	
	private boolean enabled;
	
	private String allParentIDs;
	
	CategoryAggregate()
	{
		
	}
	
	/**
	 * CommandHandler should be the constructor 
	 * with the different commands
	 * 
	 * @param comand
	 */
	@CommandHandler
	public CategoryAggregate(CategoryCommand camand)
	{
		CategoryDomainEvent event = new CategoryDomainEvent();
		BeanUtils.copyProperties(camand, event);
		
		event.setId(UUID.randomUUID().toString());
		event.setCatId(camand.getId());
		
		//Invoking or Calling Aggregate Event handler Or Publishing event to all available Aggregate Event handler
		AggregateLifecycle.apply(event);
	}
	
	// This is default event source handler
	@EventSourcingHandler
	public void onPublishEvent(CategoryDomainEvent domainEvent)
	{
		this.id				=	domainEvent.getId();
		this.allParentIDs	=	domainEvent.getAllParentIDs();
		this.alias 			= 	domainEvent.getAlias();
		this.enabled 		= 	domainEvent.isEnabled();
		this.image 			= 	domainEvent.getImage();
		this.name 			= 	domainEvent.getName();
		
		log.info("In Default event source handler");
		
		// Calling External Handler due to it is not getting invoked automatically
		
		//CategoryDomainEventPublishHandler handler = new CategoryDomainEventPublishHandler();
		
		//handler.onCategoryupdate(domainEvent);
	}
}
