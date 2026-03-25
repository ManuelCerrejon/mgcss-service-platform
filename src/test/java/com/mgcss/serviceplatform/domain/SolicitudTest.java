package com.mgcss.serviceplatform.domain;

import org.junit.jupiter.api.Test;

import com.mgcss.serviceplatform.domain.enums.EstadoSolicitud;

import static org.junit.jupiter.api.Assertions.*;

public class SolicitudTest {

		@Test
		void noSePuedeCerrarSolicitudSiNoEstaEnProceso() {
	        Solicitud solicitud = new Solicitud();
	        solicitud.setEstado(EstadoSolicitud.ABIERTA);

	        assertThrows(IllegalStateException.class, solicitud::cerrar);
	    }
		
		@Test
		void noSePuedeAsignarTecnicoInactivo() {
		    // Arrange
		    Tecnico tecnico = new Tecnico("Juan", false);
		    Solicitud solicitud = new Solicitud();
		    solicitud.setEstado(EstadoSolicitud.ABIERTA);

		    // Act & Assert
		    assertThrows(IllegalArgumentException.class, () -> {
		        solicitud.asignarTecnico(tecnico);
		    });
		}
}
