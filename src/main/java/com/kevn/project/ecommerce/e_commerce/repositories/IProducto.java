package com.kevn.project.ecommerce.e_commerce.repositories;

import org.springframework.data.repository.CrudRepository;

import com.kevn.project.ecommerce.e_commerce.models.Producto;

public interface IProducto extends CrudRepository<Producto,Long> {
}
