package com.weshopify.platform.features.categories.event;

import java.io.Serializable;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Data;


@Data
public class CategoryDomainEvent implements Serializable{
	

	private static final long serialVersionUID = -5370256263280741404L;

	private String id;
	
	private int catId;

	private String name;
	
	private String alias;
	
	private String image;
	
	private boolean enabled;
	
	private String allParentIDs;
	
		
}
