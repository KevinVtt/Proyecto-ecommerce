package com.kevn.project.ecommerce.e_commerce.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    @JsonIgnore
    private Usuario usuario;

    public void agregarProducto(Producto producto, int cantidad) {
        ProductoCantidad productoCantidad = new ProductoCantidad(this, producto, cantidad);
        productos.add(productoCantidad);
        producto.getProductosCantidad().add(productoCantidad);
    }

    public void eliminarProducto(Producto producto) {
        productos.removeIf(pc -> pc.getProducto().equals(producto));
    }

    public float getTotalProductos(){
        float total = (float)productos.stream().mapToDouble(p -> p.getTotal()).sum();
        return total;
    }

    public void crearPedido(){
        Pedido pedido = new Pedido();
        pedido.setEstado("Pendiente");
        this.pedido = pedido;
    }

    // public String preparandoPedido(){
    //     return "Enviando";
    // }
}