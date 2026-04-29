package com.mgcss.serviceplatform.infrastructure.persistence;

import com.mgcss.serviceplatform.domain.Solicitud;
import com.mgcss.serviceplatform.domain.enums.EstadoSolicitud;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SolicitudRepositoryImplTest {

    @Mock
    private JpaSolicitudRepository jpaSolicitudRepository;

    @InjectMocks
    private SolicitudRepositoryImpl solicitudRepository;

    @Test
    void deberiaGuardarSolicitud() {
        Solicitud solicitud = new Solicitud(1L, "Incidencia", EstadoSolicitud.ABIERTA);
        SolicitudEntity entityGuardada = new SolicitudEntity(1L, "Incidencia", EstadoSolicitud.ABIERTA);

        when(jpaSolicitudRepository.save(any(SolicitudEntity.class))).thenReturn(entityGuardada);

        Solicitud resultado = solicitudRepository.save(solicitud);

        assertEquals(1L, resultado.getId());
        assertEquals("Incidencia", resultado.getDescripcion());
        assertEquals(EstadoSolicitud.ABIERTA, resultado.getEstado());
        verify(jpaSolicitudRepository).save(any(SolicitudEntity.class));
    }

    @Test
    void deberiaBuscarSolicitudPorId() {
        SolicitudEntity entity = new SolicitudEntity(1L, "Incidencia", EstadoSolicitud.ABIERTA);

        when(jpaSolicitudRepository.findById(1L)).thenReturn(Optional.of(entity));

        Optional<Solicitud> resultado = solicitudRepository.findById(1L);

        assertTrue(resultado.isPresent());
        assertEquals(1L, resultado.get().getId());
        assertEquals("Incidencia", resultado.get().getDescripcion());
        assertEquals(EstadoSolicitud.ABIERTA, resultado.get().getEstado());
    }

    @Test
    void deberiaDevolverOptionalVacioSiNoExiste() {
        when(jpaSolicitudRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Solicitud> resultado = solicitudRepository.findById(99L);

        assertTrue(resultado.isEmpty());
    }

    @Test
    void deberiaListarSolicitudes() {
        SolicitudEntity entity1 = new SolicitudEntity(1L, "Incidencia 1", EstadoSolicitud.ABIERTA);
        SolicitudEntity entity2 = new SolicitudEntity(2L, "Incidencia 2", EstadoSolicitud.EN_PROCESO);

        when(jpaSolicitudRepository.findAll()).thenReturn(List.of(entity1, entity2));

        List<Solicitud> resultado = solicitudRepository.findAll();

        assertEquals(2, resultado.size());
        assertEquals("Incidencia 1", resultado.get(0).getDescripcion());
        assertEquals("Incidencia 2", resultado.get(1).getDescripcion());
    }
}