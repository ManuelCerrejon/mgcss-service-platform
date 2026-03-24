package com.mgcss.serviceplatform.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SolicitudTest {

		@Test
		void noSePuedeCerrarSolicitudSiNoEstaEnProceso() {
	        Solicitud solicitud = new Solicitud(Estado.ABIERTA);

	        assertThrows(IllegalStateException.class, solicitud::cerrar);
	    }
}
