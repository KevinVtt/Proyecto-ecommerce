package com.kevn.project.ecommerce.e_commerce.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "item_producto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

    @Override
    public final boolean equals(Object object) {
        if (this == object) return true;
        if (object == null) return false;
        Class<?> oEffectiveClass = object instanceof HibernateProxy ? ((HibernateProxy) object).getHibernateLazyInitializer().getPersistentClass() : object.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        ItemProducto that = (ItemProducto) object;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}