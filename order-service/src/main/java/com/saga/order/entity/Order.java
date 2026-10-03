package com.saga.order.entity;

import java.math.BigDecimal;
import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Document(collection = "oders")
@Getter
@Setter
public class Order {
	@Id
	private String id;
	private String customerId;
	private String productId;
	private int quantity;
	private BigDecimal amount;
	private OrderStatus status;
	private Instant createdAt;
	private Instant updatedAt;

}
