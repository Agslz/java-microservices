package com.springboot.app.products.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.app.products.models.entity.Producto;
import com.springboot.app.products.models.service.IProductoService;

@RestController
public class ProductoController {

	// @Autowired
	// private Environment env;

	@Autowired
	@Value("${server.port}")
	private Integer port;

	@Autowired
	private IProductoService productoService;

	@GetMapping("/listar")
	private List<Producto> listar() {
		return productoService.findAll().stream().map(producto -> {
			// producto.setPort(Integer.parseInt(env.getProperty("server.port")));
			producto.setPort(port);
			return producto;
		}).collect(Collectors.toList());
	}

	@GetMapping("/ver/{id}")
	public Producto detalle(@PathVariable Long id) {
		Producto producto = productoService.findById(id);
		// producto.setPort(Integer.parseInt(env.getProperty("server.port")));
		producto.setPort(port);
		return producto;
	}

}
