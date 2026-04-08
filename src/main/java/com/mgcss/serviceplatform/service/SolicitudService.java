package com.mgcss.serviceplatform.service;

import com.mgcss.serviceplatform.domain.Solicitud;
import com.mgcss.serviceplatform.domain.SolicitudNoEncontradaException;
import com.mgcss.serviceplatform.domain.Tecnico;
import com.mgcss.serviceplatform.domain.enums.EstadoSolicitud;
import com.mgcss.serviceplatform.infrastructure.SolicitudRepository;

public class SolicitudService {

    private final SolicitudRepository solicitudRepository;

    public SolicitudService(SolicitudRepository solicitudRepository) {
        this.solicitudRepository = solicitudRepository;
    }

    public Solicitud crearSolicitud(Long id, String descripcion) {
        Solicitud solicitud = new Solicitud(id, descripcion, EstadoSolicitud.ABIERTA);
        return solicitudRepository.save(solicitud);
    }

    public Solicitud obtenerSolicitud(Long id) {
        return solicitudRepository.findById(id)
                .orElseThrow(() -> new SolicitudNoEncontradaException("Solicitud no encontrada con id: " + id));
    }
    
    public Solicitud crearSolicitudSiDescripcionValida(Long id, String descripcion) {
        if (descripcion == null || descripcion.isBlank()) {
            throw new IllegalArgumentException("La descripción no puede estar vacía");
        }

        Solicitud solicitud = new Solicitud(id, descripcion, EstadoSolicitud.ABIERTA);
        return solicitudRepository.save(solicitud);
    }
    
    public Solicitud asignarTecnico(Long solicitudId, Tecnico tecnico) {
        Solicitud solicitud = solicitudRepository.findById(solicitudId)
                .orElseThrow(() -> new SolicitudNoEncontradaException("Solicitud no encontrada con id: " + solicitudId));

        solicitud.asignarTecnico(tecnico);

        return solicitudRepository.save(solicitud);
    }
}