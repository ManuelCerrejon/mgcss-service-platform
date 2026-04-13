package com.mgcss.serviceplatform.integration;

import com.mgcss.serviceplatform.domain.enums.EstadoSolicitud;
import com.mgcss.serviceplatform.infrastructure.persistence.JpaSolicitudRepository;
import com.mgcss.serviceplatform.infrastructure.persistence.SolicitudEntity;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest
@ActiveProfiles("test")
@Tag("integration")
class JpaSolicitudRepositoryIntegrationTest {

    @Autowired
    private JpaSolicitudRepository jpaSolicitudRepository;

    @Test
    void deberiaGuardarYRecuperarSolicitud() {
        SolicitudEntity entity = new SolicitudEntity(1L, "Solicitud persistida", EstadoSolicitud.ABIERTA);

        jpaSolicitudRepository.save(entity);

        Optional<SolicitudEntity> recuperada = jpaSolicitudRepository.findById(1L);

        assertTrue(recuperada.isPresent());
        assertEquals(1L, recuperada.get().getId());
        assertEquals("Solicitud persistida", recuperada.get().getDescripcion());
        assertEquals(EstadoSolicitud.ABIERTA, recuperada.get().getEstado());
    }
}