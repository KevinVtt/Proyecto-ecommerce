package com.kevn.project.ecommerce.e_commerce.strategy;

import com.kevn.project.ecommerce.e_commerce.models.Carrito;
import com.kevn.project.ecommerce.e_commerce.models.Producto;
import com.kevn.project.ecommerce.e_commerce.models.ItemProducto;

public class ActualizarProducto implements AgregarProducto {

    @Override
    public void agregar(Carrito carrito, Producto producto, int cantidad) {

        ItemProducto itemProducto = carrito.getProductos().
                                    stream().
                                    filter(p -> p.getProducto().equals(producto)).
                                    findAny().
                                    orElseThrow();
                                    itemProducto.setCantidad(itemProducto.getCantidad() + cantidad);
                                    itemProducto.getProducto().setCantidad(itemProducto.getProducto().getCantidad() - cantidad);
    }
    
}
