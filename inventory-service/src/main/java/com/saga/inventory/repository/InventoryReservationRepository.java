package com.saga.inventory.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.saga.inventory.entity.InventoryReservation;

public interface InventoryReservationRepository extends MongoRepository<InventoryReservation, String>{
	 Optional<InventoryReservation> findByOrderId(String orderId);
}
