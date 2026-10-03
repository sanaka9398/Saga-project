package com.saga.inventory.service;

import java.time.Instant;

import org.springframework.stereotype.Service;

import com.saga.inventory.dto.InventoryRequest;
import com.saga.inventory.entity.Inventory;
import com.saga.inventory.entity.InventoryReservation;
import com.saga.inventory.entity.ReservationStatus;
import com.saga.inventory.exception.InsufficientInventoryException;
import com.saga.inventory.repository.InventoryRepository;
import com.saga.inventory.repository.InventoryReservationRepository;

@Service
public class InventoryService {
	private final InventoryRepository inventoryRepository;
	private final InventoryReservationRepository inventoryReservationRepository;
	public InventoryService(InventoryRepository inventoryRepository,InventoryReservationRepository inventoryReservationRepository) {
		this.inventoryRepository=inventoryRepository;
		this.inventoryReservationRepository=inventoryReservationRepository;
	}
	public void reserve(InventoryRequest request) {
		var existingReservation= inventoryReservationRepository.findByOrderId(request.orderId());
		if(existingReservation.isPresent()) {
			InventoryReservation reservation=existingReservation.get();
			if(reservation.getStatus() == ReservationStatus.RESERVED) {
				return;
			}
			
			throw new IllegalStateException(
	                "Inventory reservation already processed for order: "
	                        + request.orderId()
	        );
			
		}
		
		Inventory inventory=inventoryRepository.findByProductId(request.productId()).orElseThrow(() ->
        new IllegalArgumentException(
                "Product not found: " + request.productId()
        			)
				);
		if(inventory.getAvailableQuantity()<request.quantity()) {
			throw new InsufficientInventoryException(  "Insufficient inventory for product: "
                    + request.productId());
		}
		inventory.setAvailableQuantity(inventory.getAvailableQuantity()-request.quantity());
		inventory.setReservedQuantity(inventory.getReservedQuantity()+request.quantity());
		inventoryRepository.save(inventory);
		
		  Instant now = Instant.now();
		InventoryReservation reservation= new InventoryReservation();
		reservation.setOrderId(request.orderId());
	    reservation.setProductId(request.productId());
	    reservation.setQuantity(request.quantity());
	    reservation.setStatus(ReservationStatus.RESERVED);
	    reservation.setCreatedAt(now);
	    reservation.setUpdatedAt(now);
	    inventoryReservationRepository.save(reservation);
	
	}
	public void release(InventoryRequest request) {
	    InventoryReservation reservation =
	    		inventoryReservationRepository
	                    .findByOrderId(request.orderId())
	                    .orElseThrow(() ->
	                            new IllegalArgumentException(
	                                    "Reservation not found for order: "
	                                            + request.orderId()
	                            )
	                    );

	    if (reservation.getStatus() == ReservationStatus.RELEASED) {
	        return;
	    }

	    Inventory inventory = inventoryRepository.findByProductId(reservation.getProductId()).orElseThrow(() ->
	                    new IllegalArgumentException("Product not found: "+ reservation.getProductId()));

	    inventory.setAvailableQuantity(
	            inventory.getAvailableQuantity()
	                    + reservation.getQuantity()
	    );
	    inventory.setReservedQuantity(inventory.getReservedQuantity()- reservation.getQuantity());
	    inventoryRepository.save(inventory);
	    reservation.setStatus(ReservationStatus.RELEASED);
	    reservation.setUpdatedAt(Instant.now());

	    inventoryReservationRepository.save(reservation);
	}
}
