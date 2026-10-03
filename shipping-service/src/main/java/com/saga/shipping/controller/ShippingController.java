package com.saga.shipping.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saga.shipping.dto.ShipmentRequest;
import com.saga.shipping.service.ShippingService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/shipments")
public class ShippingController {
	private final ShippingService shippingService;
	public ShippingController(ShippingService shippingService) {
		this.shippingService=shippingService;
	}
	   @PostMapping
	    public ResponseEntity<Void> createShipment(
	            @Valid @RequestBody ShipmentRequest request) {

	        shippingService.createShipment(request);

	        return ResponseEntity.ok().build();
	    }
	   
	   @PostMapping("/{orderId}/cancel")
	    public ResponseEntity<Void> cancelShipment(
	            @PathVariable String orderId) {

	        shippingService.cancelShipment(orderId);

	        return ResponseEntity.ok().build();
	    }
}
