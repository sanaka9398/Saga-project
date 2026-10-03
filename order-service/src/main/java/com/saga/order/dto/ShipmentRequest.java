package com.saga.order.dto;

public record ShipmentRequest(String orderId,
        String customerId) {

}
