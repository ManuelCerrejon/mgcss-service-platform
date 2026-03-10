package com.mgcss.serviceplatform.domain;

import com.mgcss.serviceplatform.domain.enums.EstadoSolicitud;

public class Solicitud {

    private Long id;
    private String descripcion;
    private EstadoSolicitud estado;

    public Solicitud() {
    }

    public Solicitud(Long id, String descripcion, EstadoSolicitud estado) {
        this.id = id;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public EstadoSolicitud getEstado() {
        return estado;
    }

    public void setEstado(EstadoSolicitud estado) {
        this.estado = estado;
    }
}