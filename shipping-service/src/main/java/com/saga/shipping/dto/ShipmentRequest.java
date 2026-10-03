package com.saga.shipping.dto;

import jakarta.validation.constraints.NotBlank;

public record ShipmentRequest(
		@NotBlank
		String orderId,
		@NotBlank
		String customerId
		) {

}
