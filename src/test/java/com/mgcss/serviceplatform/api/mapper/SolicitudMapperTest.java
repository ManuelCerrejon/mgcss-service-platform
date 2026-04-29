package com.mgcss.serviceplatform.api.mapper;

import com.mgcss.serviceplatform.api.dto.SolicitudResponseDTO;
import com.mgcss.serviceplatform.domain.Solicitud;
import com.mgcss.serviceplatform.domain.Tecnico;
import com.mgcss.serviceplatform.domain.enums.EstadoSolicitud;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SolicitudMapperTest {

    @Test
    void deberiaMapearSolicitudSinTecnico() {
        Solicitud solicitud = new Solicitud(1L, "Incidencia", EstadoSolicitud.ABIERTA);

        SolicitudResponseDTO dto = SolicitudMapper.toResponseDTO(solicitud);

        assertEquals(1L, dto.getId());
        assertEquals("Incidencia", dto.getDescripcion());
        assertEquals(EstadoSolicitud.ABIERTA, dto.getEstado());
        assertNull(dto.getTecnicoAsignado());
    }

    @Test
    void deberiaMapearSolicitudConTecnico() {
        Solicitud solicitud = new Solicitud(1L, "Incidencia", EstadoSolicitud.ABIERTA);
        solicitud.asignarTecnico(new Tecnico("Ana", true));

        SolicitudResponseDTO dto = SolicitudMapper.toResponseDTO(solicitud);

        assertEquals("Ana", dto.getTecnicoAsignado());
    }
}