package com.apeirosai.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.apeirosai.model.Product;
import com.apeirosai.service.ProductService;

@RestController
public class ProductController {

	
	@Autowired
	private ProductService productService;
	
	@PostMapping("/products")
	public ResponseEntity<Product> addProduct(@RequestBody Product product){
		
		Product response = productService.addProduct(product);
		return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
		
	}
	
	@GetMapping("/products")
	public ResponseEntity<List<Product>> addProduct(){
		
		List<Product> response = productService.allProducts();
		return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
		
	}
	

}
