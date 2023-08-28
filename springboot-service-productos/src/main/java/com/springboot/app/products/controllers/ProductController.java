package com.springboot.app.products.controllers;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.app.products.model.entity.Product;
import com.springboot.app.products.model.service.IProductService;

@RestController
public class ProductController {

	@Autowired
	private Environment env;

	@Autowired
	private IProductService productService;

	@GetMapping("/list")
	public List<Product> list() {
		return productService.findAll().stream().map(product -> {
			product.setPort(Integer.parseInt(env.getProperty("local.server.port")));
			return product;
		}).collect(Collectors.toList());
	}

	@GetMapping("/list/{id}")
	public Product detail(@PathVariable Long id) throws InterruptedException {
		
		if(id.equals(10L)) {
			throw new IllegalStateException("Product not found");
		}
		
		if(id.equals(7L)) {
			TimeUnit.SECONDS.sleep(5L);
			throw new IllegalStateException("Product not found");
		}
		
		Product product = productService.findById(id);
		product.setPort(Integer.parseInt(env.getProperty("local.server.port")));
				
		return product;
	}

}
