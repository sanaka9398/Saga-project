package com.saga.shipping.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.saga.shipping.entity.Shipment;

public interface ShipmentRepository extends MongoRepository<Shipment, String>{
	Optional<Shipment> findByOrderId(String orderId);
}
