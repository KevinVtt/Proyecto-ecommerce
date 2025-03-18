package com.kevn.project.ecommerce.e_commerce.strategy;

import com.kevn.project.ecommerce.e_commerce.models.ItemProducto;
import com.kevn.project.ecommerce.e_commerce.models.Producto;
import com.kevn.project.ecommerce.e_commerce.models.ProductoCantidad;

public class ActualizarProducto implements AgregarProducto {

    @Override
    public void agregar(ItemProducto itemProducto, Producto producto, int cantidad) {

        ProductoCantidad productoCantidad = itemProducto.getProductos().
                                    stream().
                                    filter(p -> p.getProducto().equals(producto)).
                                    findAny().
                                    orElseThrow();
        productoCantidad.setCantidad(productoCantidad.getCantidad() + cantidad);
        productoCantidad.getProducto().setCantidad(productoCantidad.getProducto().getCantidad() - cantidad);
    }
    
}
