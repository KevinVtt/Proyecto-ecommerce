package com.kevn.project.ecommerce.e_commerce.strategy;

import com.kevn.project.ecommerce.e_commerce.models.ItemProducto;
import com.kevn.project.ecommerce.e_commerce.models.Producto;
import com.kevn.project.ecommerce.e_commerce.models.ProductoCantidad;

public class AgregarNuevoProducto implements AgregarProducto{

    @Override
    public void agregar(ItemProducto itemProducto, Producto producto, int cantidad) {
        if(itemProducto == null) { throw new RuntimeException("El item producto es nulo! ");}
        ProductoCantidad nuevoProductoCantidad = new ProductoCantidad();
        producto.setCantidad(producto.getCantidad() - cantidad);
        nuevoProductoCantidad.setProducto(producto);
        nuevoProductoCantidad.setCantidad(cantidad);
        nuevoProductoCantidad.setItemProducto(itemProducto);
        itemProducto.getProductos().add(nuevoProductoCantidad);
    }
    
}
