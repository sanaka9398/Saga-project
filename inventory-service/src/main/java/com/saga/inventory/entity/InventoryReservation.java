package com.saga.inventory.entity;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Document(collection = "inventory_reservations")
@Getter
@Setter
public class InventoryReservation {
	   @Id
	    private String id;

	    @Indexed(unique = true)
	    private String orderId;

	    private String productId;

	    private int quantity;

	    private ReservationStatus status;

	    private Instant createdAt;

	    private Instant updatedAt;

}
