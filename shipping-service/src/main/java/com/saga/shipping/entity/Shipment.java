package com.saga.shipping.entity;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Document(collection = "shipments")
@Getter
@Setter
public class Shipment {
	@Id
	private String id;
	
	@Indexed(unique = true)
	private String orderId;
	
	private String customerId;
	
	private ShipmentStatus status;

	private Instant createdAt;

	private Instant updatedAt;
}
