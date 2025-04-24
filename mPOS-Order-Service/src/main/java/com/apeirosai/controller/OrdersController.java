package com.apeirosai.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.apeirosai.model.OrderRequest;
import com.apeirosai.model.Orders;
import com.apeirosai.service.OrdersService;

@RestController
public class OrdersController {

	@Autowired
	private OrdersService ordersService;
	
	@PostMapping("/orders")
	public ResponseEntity<Orders> addProduct(@RequestBody OrderRequest ordersRequest){
		
		Orders response = ordersService.createOrder(ordersRequest.getCustomerName(), ordersRequest.getOrdersItems());
		return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
		
	}
	
	@GetMapping("/orders")
	public ResponseEntity<List<Orders> > getAllOrders(){
		
		List<Orders> response = ordersService.getAllOrders();
		return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
		
	}
}
