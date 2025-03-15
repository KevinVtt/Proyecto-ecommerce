package com.kevn.project.ecommerce.e_commerce.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.kevn.project.ecommerce.e_commerce.models.Pedido;

public interface IPedido extends CrudRepository<Pedido,Long> {

@Query("SELECT p FROM Pedido p WHERE NOT EXISTS " +
       "(SELECT 1 FROM ItemProducto i WHERE i.usuario.id = :usuarioId AND i.id = p.itemProducto.id)")
List<Pedido> findPedidosSinCoincidenciasConCarritoYUsuario(@Param("usuarioId") Long usuarioId);

}
