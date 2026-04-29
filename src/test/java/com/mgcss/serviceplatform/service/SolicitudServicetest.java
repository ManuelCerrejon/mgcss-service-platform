package com.mgcss.serviceplatform.service;

import java.util.List;
import com.mgcss.serviceplatform.domain.Solicitud;
import com.mgcss.serviceplatform.domain.SolicitudNoEncontradaException;
import com.mgcss.serviceplatform.domain.Tecnico;
import com.mgcss.serviceplatform.domain.enums.EstadoSolicitud;
import com.mgcss.serviceplatform.infrastructure.SolicitudRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SolicitudServiceTest {

    @Mock
    private SolicitudRepository solicitudRepository;

    @InjectMocks
    private SolicitudService solicitudService;

    @Test
    void deberiaCrearYGuardarSolicitudConLosDatosCorrectos() {
        when(solicitudRepository.save(any(Solicitud.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Solicitud resultado = solicitudService.crearSolicitud(1L, "Test Mockito");

        ArgumentCaptor<Solicitud> captor = ArgumentCaptor.forClass(Solicitud.class);
        verify(solicitudRepository).save(captor.capture());

        Solicitud solicitudGuardada = captor.getValue();

        assertEquals(1L, solicitudGuardada.getId());
        assertEquals("Test Mockito", solicitudGuardada.getDescripcion());
        assertEquals(EstadoSolicitud.ABIERTA, solicitudGuardada.getEstado());

        assertEquals(1L, resultado.getId());
        assertEquals("Test Mockito", resultado.getDescripcion());
        assertEquals(EstadoSolicitud.ABIERTA, resultado.getEstado());
    }

    @Test
    void deberiaDevolverSolicitudCuandoExiste() {
        Solicitud solicitud = new Solicitud(1L, "Solicitud existente", EstadoSolicitud.ABIERTA);

        when(solicitudRepository.findById(1L)).thenReturn(Optional.of(solicitud));

        Solicitud resultado = solicitudService.obtenerSolicitud(1L);

        assertEquals(1L, resultado.getId());
        assertEquals("Solicitud existente", resultado.getDescripcion());
        assertEquals(EstadoSolicitud.ABIERTA, resultado.getEstado());

        verify(solicitudRepository, times(1)).findById(1L);
        verifyNoMoreInteractions(solicitudRepository);
    }

    @Test
    void deberiaLanzarExcepcionCuandoSolicitudNoExiste() {
        when(solicitudRepository.findById(99L)).thenReturn(Optional.empty());

        SolicitudNoEncontradaException exception = assertThrows(
                SolicitudNoEncontradaException.class,
                () -> solicitudService.obtenerSolicitud(99L)
        );

        assertEquals("Solicitud no encontrada con id: 99", exception.getMessage());

        verify(solicitudRepository, times(1)).findById(99L);
    }

    @Test
    void deberiaGuardarSolicitudSiDescripcionEsValida() {
        Solicitud solicitud = new Solicitud(1L, "Descripción válida", EstadoSolicitud.ABIERTA);

        when(solicitudRepository.save(any(Solicitud.class))).thenReturn(solicitud);

        Solicitud resultado = solicitudService.crearSolicitudSiDescripcionValida(1L, "Descripción válida");

        assertEquals("Descripción válida", resultado.getDescripcion());
        verify(solicitudRepository, times(1)).save(any(Solicitud.class));
    }
    
    @Test
    void noDeberiaInteractuarConRepositorioSiDescripcionEstaVacia() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> solicitudService.crearSolicitudSiDescripcionValida(1L, " ")
        );

        assertEquals("La descripción no puede estar vacía", exception.getMessage());

        verifyNoInteractions(solicitudRepository);
    }
    
    @Test
    void deberiaAsignarTecnicoValidoYGuardarSolicitudActualizada() {
        Tecnico tecnico = new Tecnico("Ana", true);
        Solicitud solicitud = new Solicitud(1L, "Incidencia", EstadoSolicitud.ABIERTA);

        when(solicitudRepository.findById(1L)).thenReturn(Optional.of(solicitud));
        when(solicitudRepository.save(any(Solicitud.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Solicitud resultado = solicitudService.asignarTecnico(1L, tecnico);

        ArgumentCaptor<Solicitud> captor = ArgumentCaptor.forClass(Solicitud.class);
        verify(solicitudRepository).findById(1L);
        verify(solicitudRepository).save(captor.capture());

        Solicitud solicitudGuardada = captor.getValue();

        assertEquals(tecnico, solicitudGuardada.getTecnicoAsignado());
        assertEquals(tecnico, resultado.getTecnicoAsignado());

        verifyNoMoreInteractions(solicitudRepository);
    }
    
    @Test
    void deberiaLanzarExcepcionSiSolicitudNoExisteAlAsignarTecnico() {
        Tecnico tecnico = new Tecnico("Ana", true);

        when(solicitudRepository.findById(99L)).thenReturn(Optional.empty());

        SolicitudNoEncontradaException exception = assertThrows(
                SolicitudNoEncontradaException.class,
                () -> solicitudService.asignarTecnico(99L, tecnico)
        );

        assertEquals("Solicitud no encontrada con id: 99", exception.getMessage());

        verify(solicitudRepository).findById(99L);
        verifyNoMoreInteractions(solicitudRepository);
    }
    
    @Test
    void deberiaCambiarEstadoSolicitud() {
        Solicitud solicitud = new Solicitud(1L, "Incidencia", EstadoSolicitud.ABIERTA);

        when(solicitudRepository.findById(1L)).thenReturn(Optional.of(solicitud));
        when(solicitudRepository.save(any(Solicitud.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Solicitud resultado = solicitudService.cambiarEstado(1L, EstadoSolicitud.EN_PROCESO);

        assertEquals(EstadoSolicitud.EN_PROCESO, resultado.getEstado());
        verify(solicitudRepository).findById(1L);
        verify(solicitudRepository).save(solicitud);
    }

    @Test
    void deberiaReabrirSolicitudCerrada() {
        Solicitud solicitud = new Solicitud(1L, "Incidencia", EstadoSolicitud.CERRADA);

        when(solicitudRepository.findById(1L)).thenReturn(Optional.of(solicitud));
        when(solicitudRepository.save(any(Solicitud.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Solicitud resultado = solicitudService.reabrirSolicitud(1L);

        assertEquals(EstadoSolicitud.ABIERTA, resultado.getEstado());
        verify(solicitudRepository).save(solicitud);
    }

    @Test
    void noDeberiaReabrirSolicitudSiNoEstaCerrada() {
        Solicitud solicitud = new Solicitud(1L, "Incidencia", EstadoSolicitud.ABIERTA);

        when(solicitudRepository.findById(1L)).thenReturn(Optional.of(solicitud));

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> solicitudService.reabrirSolicitud(1L)
        );

        assertEquals("Solo se pueden reabrir solicitudes cerradas", exception.getMessage());
        verify(solicitudRepository).findById(1L);
        verify(solicitudRepository, never()).save(any(Solicitud.class));
    }

    @Test
    void deberiaListarSolicitudes() {
        Solicitud solicitud1 = new Solicitud(1L, "Incidencia 1", EstadoSolicitud.ABIERTA);
        Solicitud solicitud2 = new Solicitud(2L, "Incidencia 2", EstadoSolicitud.EN_PROCESO);

        when(solicitudRepository.findAll()).thenReturn(List.of(solicitud1, solicitud2));

        List<Solicitud> resultado = solicitudService.listarSolicitudes();

        assertEquals(2, resultado.size());
        assertEquals("Incidencia 1", resultado.get(0).getDescripcion());
        assertEquals("Incidencia 2", resultado.get(1).getDescripcion());
        verify(solicitudRepository).findAll();
    }
}