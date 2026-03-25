package com.mgcss.serviceplatform.domain;

import org.junit.jupiter.api.Test;

import com.mgcss.serviceplatform.domain.enums.EstadoSolicitud;

import static org.junit.jupiter.api.Assertions.*;

public class SolicitudTest {

	
	@Test
	void constructorDebeInicializarCampos() {
	    Solicitud solicitud = new Solicitud(1L, "Incidencia", EstadoSolicitud.ABIERTA);

	    assertEquals(1L, solicitud.getId());
	    assertEquals("Incidencia", solicitud.getDescripcion());
	    assertEquals(EstadoSolicitud.ABIERTA, solicitud.getEstado());
	}
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
		@Test
		void sePuedeCerrarSolicitudSiEstaEnProceso() {
		    Solicitud solicitud = new Solicitud();
		    solicitud.setEstado(EstadoSolicitud.EN_PROCESO);

		    solicitud.cerrar();

		    assertEquals(EstadoSolicitud.CERRADA, solicitud.getEstado());
		}
		@Test
		void sePuedeAsignarTecnicoActivo() {
		    Tecnico tecnico = new Tecnico("Juan", true);
		    Solicitud solicitud = new Solicitud();

		    solicitud.asignarTecnico(tecnico);

		    assertEquals(tecnico, solicitud.getTecnicoAsignado());
		}
		@Test
		void settersFuncionanCorrectamente() {
		    Solicitud solicitud = new Solicitud();

		    solicitud.setId(10L);
		    solicitud.setDescripcion("Test");
		    solicitud.setEstado(EstadoSolicitud.EN_PROCESO);

		    assertEquals(10L, solicitud.getId());
		    assertEquals("Test", solicitud.getDescripcion());
		    assertEquals(EstadoSolicitud.EN_PROCESO, solicitud.getEstado());
		}
}
