package com.springboot.app.item.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.app.item.models.Item;
import com.springboot.app.item.models.Product;
import com.springboot.app.item.models.service.ItemService;

@RestController
public class ItemController {

	@Autowired
	private CircuitBreakerFactory cbFactory;
	
	private final Logger logger = LoggerFactory.getLogger(CircuitBreakerFactory.class);

	@Autowired
	@Qualifier("serviceFeign")
	private ItemService itemService;

	@GetMapping("/list")
	public List<Item> list(@RequestParam(name = "name", required = false) String name,
			@RequestHeader(name = "token-request", required = false) String token) {
		System.out.println(name);
		System.out.println(token);
		return itemService.findAll();
	}

//	@HystrixCommand(fallbackMethod = "alternativeMethod")
	@GetMapping("/list/{id}/quantity/{quantity}")
	public Item detail(@PathVariable Long id, @PathVariable Integer quantity) {
		return cbFactory.create("item").run(() -> itemService.findById(id, quantity),
				e -> alternativeMethod(id, quantity, e));
	}

	public Item alternativeMethod(Long id, Integer quantity, Throwable e) {
		logger.info(e.getMessage());
		Item item = new Item();
		Product product = new Product();
		item.setQuantity(quantity);
		product.setId(id);
		product.setName("Camera Sony");
		product.setPrice(500.00);
		item.setProduct(product);
		return item;
	}

}
