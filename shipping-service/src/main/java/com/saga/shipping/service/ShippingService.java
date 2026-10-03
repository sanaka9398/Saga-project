package com.saga.shipping.service;

import java.time.Instant;

import org.springframework.stereotype.Service;

import com.saga.shipping.dto.ShipmentRequest;
import com.saga.shipping.entity.Shipment;
import com.saga.shipping.entity.ShipmentStatus;
import com.saga.shipping.exception.ShipmentFailedException;
import com.saga.shipping.repository.ShipmentRepository;

@Service
public class ShippingService {
	private final ShipmentRepository shipmentRepository;
	public ShippingService(ShipmentRepository shipmentRepository) {
		this.shipmentRepository=shipmentRepository;
	}
	public void createShipment(ShipmentRequest request) {
		  var existingShipment = shipmentRepository.findByOrderId(request.orderId());
		  
		   if (existingShipment.isPresent()) {
			   Shipment shipment=existingShipment.get();
			   if (shipment.getStatus() == ShipmentStatus.CREATED) {
	                return;
	            }
			   if(shipment.getStatus() == ShipmentStatus.CANCELLED) {
				   throw new IllegalStateException(
	                        "Shipment was already cancelled for order: "
	                                + request.orderId()
	                );
			   }
		   }
		   if ("FAIL_SHIPPING".equalsIgnoreCase(request.customerId())) {
	            throw new ShipmentFailedException(
	                    "Shipment creation failed for order: "
	                            + request.orderId()
	            );
	        }
		   Instant now = Instant.now();
		   Shipment shipment=new Shipment();
		   shipment.setOrderId(request.orderId());
		   shipment.setCustomerId(request.customerId());
		   shipment.setStatus(ShipmentStatus.CREATED);
		   shipment.setCreatedAt(now);
		   shipment.setUpdatedAt(now);
		   shipmentRepository.save(shipment);

	}
	
	//cancell shipment
	public void cancelShipment(String orderId) {
		Shipment shipment=shipmentRepository.findByOrderId(orderId).orElseThrow(
				()->new IllegalStateException("shipment not found for the order: "+orderId));
		
		if(shipment.getStatus() == ShipmentStatus.CANCELLED) {
			return;
		}
		if(shipment.getStatus() != ShipmentStatus.CREATED) {
			throw new IllegalStateException("only created shipment can be Cancelled for the order: "+orderId);
			
		}
		 shipment.setStatus(ShipmentStatus.CANCELLED);
		 shipment.setUpdatedAt(Instant.now());

		 shipmentRepository.save(shipment);
	}
}
