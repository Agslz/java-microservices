package com.pringboot.app.products.model.service;

import java.util.List;

import com.pringboot.app.products.model.entity.Product;

public interface IProductService {
	
	public List<Product> findAll();
	
	public Product findById(Long id);

}
