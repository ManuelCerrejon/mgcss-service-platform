package com.mgcss.serviceplatform.api.dto;

import jakarta.validation.constraints.NotBlank;

public class AsignarTecnicoRequestDTO {

    @NotBlank(message = "El nombre del técnico no puede estar vacío")
    private String nombre;

    private boolean activo;

    public AsignarTecnicoRequestDTO() {
    }

    public AsignarTecnicoRequestDTO(String nombre, boolean activo) {
        this.nombre = nombre;
        this.activo = activo;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}