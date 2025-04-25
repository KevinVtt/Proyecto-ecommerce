package com.kevn.project.ecommerce.e_commerce.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

@Embeddable
public class Fecha {

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaInicio;

    @Column(name = "fecha_entrega")
    private LocalDateTime fechaFin;


    @PrePersist
    public void prePersist(){
        this.fechaInicio = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate(){

        LocalDateTime fechaActual = LocalDateTime.now();
        this.fechaFin = fechaActual.plusDays(2);
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }

}
