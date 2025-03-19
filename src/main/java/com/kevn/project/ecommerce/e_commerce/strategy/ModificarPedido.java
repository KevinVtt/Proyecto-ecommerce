package com.kevn.project.ecommerce.e_commerce.strategy;
import com.kevn.project.ecommerce.e_commerce.models.ItemProducto;
import com.kevn.project.ecommerce.e_commerce.models.Pedido;

public interface ModificarPedido {
    Pedido asignarEstado(ItemProducto t);
}
