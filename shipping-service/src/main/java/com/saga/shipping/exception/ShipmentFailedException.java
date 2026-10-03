package com.saga.shipping.exception;

public class ShipmentFailedException extends RuntimeException{
	public ShipmentFailedException(String message) {
		super(message);
	}
}
