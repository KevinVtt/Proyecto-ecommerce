package com.kevn.project.ecommerce.e_commerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kevn.project.ecommerce.e_commerce.models.Producto;

public interface IProducto extends JpaRepository<Producto,Long> {
    boolean existsByNombre(String nombre);
    
}
