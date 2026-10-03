package com.saga.order.dto;

import java.math.BigDecimal;

public record PaymentRequest(
		String orderId,
		String customerId,
		BigDecimal amount) {

}
