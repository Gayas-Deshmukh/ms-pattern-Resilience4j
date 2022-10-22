package com.weshopify.platform.features.categories.command;

import java.io.Serializable;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Data;

@Data
public class CategoryCommand implements Serializable
{
	
	private static final long serialVersionUID = 3334156279042412725L;

	/**
	 * Aggregator will identify command with this annotation
	 * failing to add it, causing the runtime error
	 */
	@TargetAggregateIdentifier
	private int id;
	
	private String name;
	
	private String alias;
	
	private String image;
	
	private boolean enabled;
	
	private String allParentIDs;
	
}
