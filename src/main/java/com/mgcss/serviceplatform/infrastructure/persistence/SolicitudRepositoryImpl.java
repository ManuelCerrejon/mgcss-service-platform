package com.mgcss.serviceplatform.infrastructure.persistence;

import java.util.List;
import com.mgcss.serviceplatform.domain.Solicitud;
import com.mgcss.serviceplatform.infrastructure.SolicitudRepository;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class SolicitudRepositoryImpl implements SolicitudRepository {

    private final JpaSolicitudRepository jpaSolicitudRepository;

    public SolicitudRepositoryImpl(JpaSolicitudRepository jpaSolicitudRepository) {
        this.jpaSolicitudRepository = jpaSolicitudRepository;
    }

    @Override
    public Solicitud save(Solicitud solicitud) {
        SolicitudEntity entity = toEntity(solicitud);
        SolicitudEntity savedEntity = jpaSolicitudRepository.save(entity);
        return toDomain(savedEntity);
    }

    @Override
    public Optional<Solicitud> findById(Long id) {
        return jpaSolicitudRepository.findById(id)
                .map(this::toDomain);
    }

    private SolicitudEntity toEntity(Solicitud solicitud) {
        return new SolicitudEntity(
                solicitud.getId(),
                solicitud.getDescripcion(),
                solicitud.getEstado()
        );
    }

    private Solicitud toDomain(SolicitudEntity entity) {
        return new Solicitud(
                entity.getId(),
                entity.getDescripcion(),
                entity.getEstado()
        );
    }
    
    @Override
    public List<Solicitud> findAll() {
        return jpaSolicitudRepository.findAll()
                .stream()
                .map(this::toDomain)
                .toList();
    }
}