package com.kevn.project.ecommerce.e_commerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.kevn.project.ecommerce.e_commerce.models.Pedido;

public interface IPedido extends JpaRepository<Pedido,Long> {
}
