package com.kevn.project.ecommerce.e_commerce.strategy;

import com.kevn.project.ecommerce.e_commerce.models.Carrito;
import com.kevn.project.ecommerce.e_commerce.models.Producto;
import com.kevn.project.ecommerce.e_commerce.models.ItemProducto;

public class AgregarNuevoProducto implements AgregarProducto{

    @Override
    public void agregar(Carrito carrito, Producto producto, int cantidad) {
        if(carrito == null) { throw new RuntimeException("El item producto es nulo! ");}
        ItemProducto itemProducto = new ItemProducto();
        producto.setCantidad(producto.getCantidad() - cantidad);
        itemProducto.setProducto(producto);
        itemProducto.setCantidad(cantidad);
        itemProducto.setCarrito(carrito);
        carrito.getProductos().add(itemProducto);
    }
    
}
