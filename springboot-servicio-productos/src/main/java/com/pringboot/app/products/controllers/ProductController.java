package com.pringboot.app.products.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.pringboot.app.products.model.entity.Product;
import com.pringboot.app.products.model.service.IProductService;

@RestController
public class ProductController {

	@Autowired
	private Environment env;

	@Autowired
	private IProductService productService;

	@GetMapping("/list")
	public List<Product> list() {
		return productService.findAll().stream().map(product ->{
			product.setPort(Integer.parseInt(env.getProperty("local.server.port")));
			return product;
		}).collect(Collectors.toList());
	}

	@GetMapping("/list/{id}")
	public Product detail(@PathVariable Long id) {
		Product product = productService.findById(id);
		product.setPort(Integer.parseInt(env.getProperty("local.server.port")));
		return product;
	}

}
