package com.springboot.app.item.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.springboot.app.item.models.Item;
import com.springboot.app.item.models.Product;
import com.springboot.app.item.models.service.ItemService;

@RestController
public class ItemController {

	@Autowired
	@Qualifier("serviceFeign")
	private ItemService itemService;

	@GetMapping("/list")
	public List<Item> list() {
		return itemService.findAll();
	}

	@HystrixCommand(fallbackMethod = "AlternativeMethod")
	@GetMapping("/list/{id}/quantity/{quantity}")
	public Item detail(Long id, Integer quantity) {
		return itemService.findById(id, quantity);
	}

	public Item AlternativeMethod(Long id, Integer quantity) {
		Item item = new Item();
		Product product = new Product();
		item.setQuantity(quantity);
		product.setId(id);
		product.setName("Camara Sony");
		product.setPrice(500.00);
		item.setProduct(product);
		return item;

	}
}
