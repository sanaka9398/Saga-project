package com.saga.inventory.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saga.inventory.dto.InventoryRequest;
import com.saga.inventory.service.InventoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
	private final InventoryService inventoryService;
	public InventoryController(InventoryService inventoryService) {
		this.inventoryService=inventoryService;
	}
	
	@PostMapping("/reserve")
    public ResponseEntity<Void> reserve(
            @Valid @RequestBody InventoryRequest request) {

        inventoryService.reserve(request);

        return ResponseEntity.ok().build();
    }
	
	  @PostMapping("/release")
	    public ResponseEntity<Void> release(
	            @Valid @RequestBody InventoryRequest request) {

	        inventoryService.release(request);

	        return ResponseEntity.ok().build();
	    }
}
