package com.apeirosai.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apeirosai.model.Product;
import com.apeirosai.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepo;
	
	@Override
	public Product addProduct(Product p) {
		// TODO Auto-generated method stub
		
		Product product = productRepo.save(p);
		
		return product;
	}

	@Override
	public List<Product> allProducts() {
		// TODO Auto-generated method stub
		
		List<Product> products = productRepo.findAll();
		
		return products;
	}

}
