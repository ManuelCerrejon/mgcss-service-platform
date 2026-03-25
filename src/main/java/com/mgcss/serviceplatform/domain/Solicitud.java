package com.mgcss.serviceplatform.domain;

import com.mgcss.serviceplatform.domain.*;

public class Solicitud {

    private Long id;
    private String descripcion;
    private EstadoSolicitud estado;
    private Tecnico tecnicoAsignado;
 
    
    public Solicitud() {
    }

    public Solicitud(Long id, String descripcion, EstadoSolicitud estado) {
        this.id = id;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    public void asignarTecnico(Tecnico tecnico) {
        validarTecnicoAsignable(tecnico);
        this.tecnicoAsignado = tecnico;
    }

    private void validarTecnicoAsignable(Tecnico tecnico) {
        if (tecnico == null) {
            throw new IllegalArgumentException("El técnico no puede ser null");
        }

        if (!tecnico.estaActivo()) {
            throw new IllegalArgumentException("No se puede asignar un técnico inactivo");
        }
    }
    
    public void cerrar() {
    	validarQueEstaEnProceso();
        this.estado = EstadoSolicitud.CERRADA;
    }

    private void validarQueEstaEnProceso() {
        if (this.estado != EstadoSolicitud.EN_PROCESO) {
            throw new IllegalStateException("Estado inválido para cierre");
        }
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

    public void setEstado(EstadoSolicitud nuevoEstado) {
        this.estado = nuevoEstado;
    }
}