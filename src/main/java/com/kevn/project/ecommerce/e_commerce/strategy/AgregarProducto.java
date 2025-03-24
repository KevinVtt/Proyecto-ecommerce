package com.kevn.project.ecommerce.e_commerce.strategy;

import com.kevn.project.ecommerce.e_commerce.models.Carrito;
import com.kevn.project.ecommerce.e_commerce.models.Producto;

public interface AgregarProducto {
    void agregar(Carrito carrito, Producto producto, int cantidad);
}
