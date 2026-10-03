package com.saga.inventory.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.saga.inventory.entity.Inventory;

public interface InventoryRepository extends MongoRepository<Inventory, String>{
	
	  Optional<Inventory> findByProductId(String productId);
}
