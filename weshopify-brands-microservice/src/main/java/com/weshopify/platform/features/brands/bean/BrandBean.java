package com.weshopify.platform.features.brands.bean;

import java.io.Serializable;
import java.util.List;

import lombok.Data;


@Data
public class BrandBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;

	private String name;
	
	private String logo;
	
    //private Set<CategoryBean> categories = new HashSet<>();
	
	private List<Object> categories;
   
    private String fileImage;
}
