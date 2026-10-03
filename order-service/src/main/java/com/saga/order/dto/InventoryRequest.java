package com.saga.order.dto;

public record InventoryRequest( String orderId,
        String productId,
        int quantity) {

}
