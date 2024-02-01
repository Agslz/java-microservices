package com.springboot.app.products.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.app.products.models.dao.IProductoDao;
import com.springboot.app.products.models.entity.Producto;

@Service
public class ProductoServiceImpl implements IProductoService {

	@Autowired
	private IProductoDao productoDao;

	@Override 
	@Transactional(readOnly = true)
	public List<Producto> findAll() {
		return (List<Producto>) productoDao.findAll();

	}

	@Override
	@Transactional(readOnly = true)
	public Producto findById(Long id) {

		return productoDao.findById(id).orElse(null);
	}

}
