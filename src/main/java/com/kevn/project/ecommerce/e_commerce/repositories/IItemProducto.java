package com.kevn.project.ecommerce.e_commerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kevn.project.ecommerce.e_commerce.models.ItemProducto;

public interface IItemProducto extends JpaRepository<ItemProducto,Long> {

}
