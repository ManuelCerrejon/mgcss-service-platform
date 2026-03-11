package com.mgcss.serviceplatform.service;

import com.mgcss.serviceplatform.domain.Solicitud;
import com.mgcss.serviceplatform.domain.enums.EstadoSolicitud;

public class SolicitudService {

    public Solicitud crearSolicitud(Long id, String descripcion) {
    	return new Solicitud(id, descripcion, EstadoSolicitud.ABIERTAa);    }
}