package com.saga.order.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateOrderRequest(
		   	@NotBlank
		   String customerId,
		   
		   	@NotBlank
	        String productId,
	        
        	@Min(1)
			int quantity,
        
			@NotNull
			@DecimalMin("0.01")
			BigDecimal amount
        
		) {

}
