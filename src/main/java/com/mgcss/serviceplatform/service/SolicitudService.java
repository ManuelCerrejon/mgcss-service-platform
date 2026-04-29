package com.mgcss.serviceplatform.service;

import org.springframework.stereotype.Service;
import java.util.List;
import com.mgcss.serviceplatform.domain.Solicitud;
import com.mgcss.serviceplatform.domain.SolicitudNoEncontradaException;
import com.mgcss.serviceplatform.domain.Tecnico;
import com.mgcss.serviceplatform.domain.enums.EstadoSolicitud;
import com.mgcss.serviceplatform.infrastructure.SolicitudRepository;

@Service
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
    
    public Solicitud cambiarEstado(Long solicitudId, EstadoSolicitud nuevoEstado) {
        Solicitud solicitud = obtenerSolicitud(solicitudId);
        solicitud.setEstado(nuevoEstado);
        return solicitudRepository.save(solicitud);
    }

    public Solicitud reabrirSolicitud(Long solicitudId) {
        Solicitud solicitud = obtenerSolicitud(solicitudId);

        if (solicitud.getEstado() != EstadoSolicitud.CERRADA) {
            throw new IllegalStateException("Solo se pueden reabrir solicitudes cerradas");
        }

        solicitud.setEstado(EstadoSolicitud.ABIERTA);
        return solicitudRepository.save(solicitud);
    }

    public List<Solicitud> listarSolicitudes() {
        return solicitudRepository.findAll();
    }
}