package com.mgcss.serviceplatform.service;


import org.junit.jupiter.api.Test;

import com.mgcss.serviceplatform.domain.Solicitud;
import com.mgcss.serviceplatform.domain.enums.EstadoSolicitud;

import static org.junit.jupiter.api.Assertions.*;

public class SolicitudServicetest {
	@Test
	void crearSolicitudDebeCrearSolicitudCorrectamente() {
	    SolicitudService service = new SolicitudService();

	    Solicitud solicitud = service.crearSolicitud(1L, "Problema X");

	    assertNotNull(solicitud);
	    assertEquals("Problema X", solicitud.getDescripcion());
	    assertEquals(EstadoSolicitud.ABIERTA, solicitud.getEstado());
	}

}
