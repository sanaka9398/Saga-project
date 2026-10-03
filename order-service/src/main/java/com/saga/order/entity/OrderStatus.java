package com.saga.order.entity;

public enum OrderStatus {
	PENDING,
	INVENTORY_RESERVED,
	PAYMENT_COMPLETED,
	SHIPMENT_CREATED,
	CONFIRMED,
	CANCELLED
}
