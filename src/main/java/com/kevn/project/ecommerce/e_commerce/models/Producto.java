package com.kevn.project.ecommerce.e_commerce.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "producto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre = "";
    private Double precio = 0d;
    private String descripcion = "";
    private String tipo = "";
    private Integer cantidad = 0;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<ProductoCantidad> productosCantidad = new ArrayList<>();

    public Producto(Long id) {
        this.id = id;
    }

    

}