package com.kevn.project.ecommerce.e_commerce.strategy;
import com.kevn.project.ecommerce.e_commerce.models.Carrito;
import com.kevn.project.ecommerce.e_commerce.models.Pedido;

public interface ModificarPedido {
    Pedido asignarEstado(Carrito t);
    void updateEstadoPedido(Carrito it);
}
