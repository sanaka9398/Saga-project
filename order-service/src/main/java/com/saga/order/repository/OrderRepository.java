package com.saga.order.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.saga.order.entity.Order;

public interface OrderRepository extends MongoRepository<Order, String>{

}
