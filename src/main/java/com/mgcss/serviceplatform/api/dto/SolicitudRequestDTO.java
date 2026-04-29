package com.mgcss.serviceplatform.api.dto;

import jakarta.validation.constraints.NotBlank;

public class SolicitudRequestDTO {

    @NotBlank(message = "La descripción no puede estar vacía")
    private String descripcion;

    public SolicitudRequestDTO() {
    }

    public SolicitudRequestDTO(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}