package com.mgcss.serviceplatform.api.dto;

import com.mgcss.serviceplatform.domain.enums.EstadoSolicitud;
import jakarta.validation.constraints.NotNull;

public class CambioEstadoRequestDTO {

    @NotNull(message = "El estado no puede ser null")
    private EstadoSolicitud estado;

    public CambioEstadoRequestDTO() {
    }

    public CambioEstadoRequestDTO(EstadoSolicitud estado) {
        this.estado = estado;
    }

    public EstadoSolicitud getEstado() {
        return estado;
    }

    public void setEstado(EstadoSolicitud estado) {
        this.estado = estado;
    }
}