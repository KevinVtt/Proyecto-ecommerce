package com.kevn.project.ecommerce.e_commerce.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;


/*
 * No utilizar el producto cantidad para eliminar.
 * Ya que si lo utilizamos eliminariamos item_producto y el pedido.
 */
@Entity
@Table(name = "producto_cantidad")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class ProductoCantidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "item_producto_id", nullable = false)
    @JsonIgnore
    private ItemProducto itemProducto;
    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = true)
    private Producto producto;

    private Integer cantidad = 0;

    public ProductoCantidad(ItemProducto itemProducto, Producto producto, int cantidad) {
        this.itemProducto = itemProducto;
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public Float getTotal(){
        return (float) (this.cantidad * producto.getPrecio());
    }
}