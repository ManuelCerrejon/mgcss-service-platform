package com.mgcss.serviceplatform.api.dto;

import com.mgcss.serviceplatform.domain.enums.EstadoSolicitud;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DtoTest {

    @Test
    void deberiaCubrirSolicitudRequestDTO() {
        SolicitudRequestDTO dto = new SolicitudRequestDTO("Incidencia");

        assertEquals("Incidencia", dto.getDescripcion());

        dto.setDescripcion("Nueva");
        assertEquals("Nueva", dto.getDescripcion());
    }

    @Test
    void deberiaCubrirSolicitudResponseDTO() {
        SolicitudResponseDTO dto = new SolicitudResponseDTO(
                1L,
                "Incidencia",
                EstadoSolicitud.ABIERTA,
                "Ana"
        );

        assertEquals(1L, dto.getId());
        assertEquals("Incidencia", dto.getDescripcion());
        assertEquals(EstadoSolicitud.ABIERTA, dto.getEstado());
        assertEquals("Ana", dto.getTecnicoAsignado());

        dto.setId(2L);
        dto.setDescripcion("Nueva");
        dto.setEstado(EstadoSolicitud.CERRADA);
        dto.setTecnicoAsignado("Juan");

        assertEquals(2L, dto.getId());
        assertEquals("Nueva", dto.getDescripcion());
        assertEquals(EstadoSolicitud.CERRADA, dto.getEstado());
        assertEquals("Juan", dto.getTecnicoAsignado());
    }

    @Test
    void deberiaCubrirCambioEstadoRequestDTO() {
        CambioEstadoRequestDTO dto = new CambioEstadoRequestDTO(EstadoSolicitud.EN_PROCESO);

        assertEquals(EstadoSolicitud.EN_PROCESO, dto.getEstado());

        dto.setEstado(EstadoSolicitud.CERRADA);
        assertEquals(EstadoSolicitud.CERRADA, dto.getEstado());
    }

    @Test
    void deberiaCubrirAsignarTecnicoRequestDTO() {
        AsignarTecnicoRequestDTO dto = new AsignarTecnicoRequestDTO("Ana", true);

        assertEquals("Ana", dto.getNombre());
        assertTrue(dto.isActivo());

        dto.setNombre("Juan");
        dto.setActivo(false);

        assertEquals("Juan", dto.getNombre());
        assertFalse(dto.isActivo());
    }
}