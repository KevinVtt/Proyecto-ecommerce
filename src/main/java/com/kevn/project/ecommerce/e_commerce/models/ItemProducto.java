package com.kevn.project.ecommerce.e_commerce.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "item_producto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ItemProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "itemProducto", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<ProductoCantidad> productos = new ArrayList<>();

    @OneToOne(mappedBy = "itemProducto", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    public void agregarProducto(Producto producto, int cantidad) {
        ProductoCantidad productoCantidad = new ProductoCantidad(this, producto, cantidad);
        productos.add(productoCantidad);
        producto.getProductosCantidad().add(productoCantidad);
    }

    public void eliminarProducto(Producto producto) {
        productos.removeIf(pc -> pc.getProducto().equals(producto));
    }
}