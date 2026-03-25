package com.mgcss.serviceplatform.domain;

public class Tecnico {
    private String nombre;
    private boolean activo;

    public Tecnico(String nombre, boolean activo) {
        this.nombre = nombre;
        this.activo = activo;
    }

    public boolean estaActivo() {
        return activo;
    }
}
