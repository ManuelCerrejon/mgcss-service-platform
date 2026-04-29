package com.mgcss.serviceplatform.infrastructure;

import com.mgcss.serviceplatform.domain.Solicitud;

import java.util.List;
import java.util.Optional;

public interface SolicitudRepository {

    Solicitud save(Solicitud solicitud);

    Optional<Solicitud> findById(Long id);

    List<Solicitud> findAll();
}