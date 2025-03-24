package com.kevn.project.ecommerce.e_commerce.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;


/*
 * No utilizar el producto cantidad para eliminar.
 * Ya que si lo utilizamos eliminariamos item_producto y el pedido.
 */
@Entity
@Table(name = "item_producto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class ItemProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "carrito_id", nullable = false)
    @JsonIgnore
    private Carrito carrito;
    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = true)
    private Producto producto;

    private Integer cantidad = 0;

    public ItemProducto(Carrito carrito, Producto producto, int cantidad) {
        this.carrito = carrito;
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public Float getTotal(){
        return (float) (this.cantidad * producto.getPrecio());
    }
}