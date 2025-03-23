package com.kevn.project.ecommerce.e_commerce.models;

import jakarta.persistence.*;
import lombok.*;

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

    public Producto(Long id) {
        this.id = id;
    }

    

}