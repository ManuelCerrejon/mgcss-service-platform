package com.mgcss.serviceplatform.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaSolicitudRepository extends JpaRepository<SolicitudEntity, Long> {
}