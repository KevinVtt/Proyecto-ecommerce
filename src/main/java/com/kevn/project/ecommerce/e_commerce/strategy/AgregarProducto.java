package com.kevn.project.ecommerce.e_commerce.strategy;

import com.kevn.project.ecommerce.e_commerce.models.ItemProducto;
import com.kevn.project.ecommerce.e_commerce.models.Producto;

public interface AgregarProducto {
    void agregar(ItemProducto itemProducto, Producto producto, int cantidad);
}
