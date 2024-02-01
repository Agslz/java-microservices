package com.springboot.app.products.models.service;

import java.util.List;

import com.springboot.app.products.models.entity.Producto;

public interface IProductoService {

	public List<Producto> findAll();
	public Producto findById(Long id);
	
	
}
