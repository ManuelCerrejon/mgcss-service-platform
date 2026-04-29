package com.mgcss.serviceplatform.api.dto;

import com.mgcss.serviceplatform.domain.enums.EstadoSolicitud;

public class SolicitudResponseDTO {

    private Long id;
    private String descripcion;
    private EstadoSolicitud estado;
    private String tecnicoAsignado;

    public SolicitudResponseDTO() {
    }

    public SolicitudResponseDTO(Long id, String descripcion, EstadoSolicitud estado, String tecnicoAsignado) {
        this.id = id;
        this.descripcion = descripcion;
        this.estado = estado;
        this.tecnicoAsignado = tecnicoAsignado;
    }

    public Long getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public EstadoSolicitud getEstado() {
        return estado;
    }

    public String getTecnicoAsignado() {
        return tecnicoAsignado;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setEstado(EstadoSolicitud estado) {
        this.estado = estado;
    }

    public void setTecnicoAsignado(String tecnicoAsignado) {
        this.tecnicoAsignado = tecnicoAsignado;
    }
}