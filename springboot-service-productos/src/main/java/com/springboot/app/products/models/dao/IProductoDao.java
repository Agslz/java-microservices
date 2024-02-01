package com.springboot.app.products.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.springboot.app.products.models.entity.Producto;

public interface IProductoDao extends CrudRepository<Producto, Long> {

	 
	
	
}
