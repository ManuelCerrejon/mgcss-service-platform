package com.mgcss.serviceplatform.api.mapper;

import com.mgcss.serviceplatform.api.dto.SolicitudResponseDTO;
import com.mgcss.serviceplatform.domain.Solicitud;

public class SolicitudMapper {

    private SolicitudMapper() {
    }

    public static SolicitudResponseDTO toResponseDTO(Solicitud solicitud) {
        String tecnicoAsignado = null;

        if (solicitud.getTecnicoAsignado() != null) {
            tecnicoAsignado = solicitud.getTecnicoAsignado().getNombre();
        }

        return new SolicitudResponseDTO(
                solicitud.getId(),
                solicitud.getDescripcion(),
                solicitud.getEstado(),
                tecnicoAsignado
        );
    }
}