package com.apeirosai.service;

import java.util.List;

import com.apeirosai.model.Product;

public interface ProductService {

	public Product addProduct(Product p);
	public List<Product> allProducts();
}
