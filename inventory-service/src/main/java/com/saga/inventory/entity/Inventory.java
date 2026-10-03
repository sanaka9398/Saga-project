package com.saga.inventory.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Document(collection ="inventory")
@Getter
@Setter
public class Inventory {
	@Id
	private String id;
	
	@Indexed(unique = true)
	private String productId;
	
	private int availableQuantity;
	private int reservedQuantity;
	
	
}
