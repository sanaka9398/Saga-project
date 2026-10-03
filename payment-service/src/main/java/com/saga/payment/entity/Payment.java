package com.saga.payment.entity;

import java.math.BigDecimal;
import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Document(collection = "payments")
@Getter
@Setter
public class Payment {
	@Id
	private String id;
	
	@Indexed(unique = true)
	private String orderId;
	
	private String customerId;
	
	private BigDecimal amount;
	
	private PaymentStatus status;
	
	private Instant createdAt;
	
	private Instant updatedAt;
	
}
