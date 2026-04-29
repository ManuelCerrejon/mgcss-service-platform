package com.mgcss.serviceplatform.api;


import com.mgcss.serviceplatform.domain.Solicitud;
import com.mgcss.serviceplatform.domain.enums.EstadoSolicitud;
import com.mgcss.serviceplatform.domain.Tecnico;
import com.mgcss.serviceplatform.service.SolicitudService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;

@WebMvcTest(SolicitudController.class)
class SolicitudControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SolicitudService solicitudService;

    @Test
    void deberiaCrearSolicitud() throws Exception {
        Solicitud solicitud = new Solicitud(1L, "Incidencia", EstadoSolicitud.ABIERTA);

        when(solicitudService.crearSolicitudSiDescripcionValida(isNull(), any(String.class)))
                .thenReturn(solicitud);

        String json = """
                {
                  "descripcion": "Incidencia"
                }
                """;

        mockMvc.perform(post("/api/solicitudes")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.descripcion").value("Incidencia"))
                .andExpect(jsonPath("$.estado").value("ABIERTA"));
    }

    @Test
    void deberiaObtenerSolicitudPorId() throws Exception {
        Solicitud solicitud = new Solicitud(1L, "Solicitud existente", EstadoSolicitud.ABIERTA);

        when(solicitudService.obtenerSolicitud(1L)).thenReturn(solicitud);

        mockMvc.perform(get("/api/solicitudes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.descripcion").value("Solicitud existente"))
                .andExpect(jsonPath("$.estado").value("ABIERTA"));
    }
    
    @Test
    void deberiaListarSolicitudes() throws Exception {
        Solicitud solicitud1 = new Solicitud(1L, "Incidencia 1", EstadoSolicitud.ABIERTA);
        Solicitud solicitud2 = new Solicitud(2L, "Incidencia 2", EstadoSolicitud.EN_PROCESO);

        when(solicitudService.listarSolicitudes())
                .thenReturn(List.of(solicitud1, solicitud2));

        mockMvc.perform(get("/api/solicitudes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].descripcion").value("Incidencia 1"))
                .andExpect(jsonPath("$[0].estado").value("ABIERTA"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].descripcion").value("Incidencia 2"))
                .andExpect(jsonPath("$[1].estado").value("EN_PROCESO"));
    }
    
    @Test
    void deberiaCambiarEstadoSolicitud() throws Exception {
        Solicitud solicitud = new Solicitud(1L, "Incidencia", EstadoSolicitud.EN_PROCESO);

        when(solicitudService.cambiarEstado(eq(1L), eq(EstadoSolicitud.EN_PROCESO)))
                .thenReturn(solicitud);

        String json = """
                {
                  "estado": "EN_PROCESO"
                }
                """;

        mockMvc.perform(put("/api/solicitudes/1/estado")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.descripcion").value("Incidencia"))
                .andExpect(jsonPath("$.estado").value("EN_PROCESO"));
    }
    
    
    @Test
    void deberiaAsignarTecnico() throws Exception {
        Tecnico tecnico = new Tecnico("Ana", true);
        Solicitud solicitud = new Solicitud(1L, "Incidencia", EstadoSolicitud.ABIERTA);
        solicitud.asignarTecnico(tecnico);

        when(solicitudService.asignarTecnico(eq(1L), any(Tecnico.class)))
                .thenReturn(solicitud);

        String json = """
                {
                  "nombre": "Ana",
                  "activo": true
                }
                """;

        mockMvc.perform(put("/api/solicitudes/1/tecnico")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.descripcion").value("Incidencia"))
                .andExpect(jsonPath("$.estado").value("ABIERTA"))
                .andExpect(jsonPath("$.tecnicoAsignado").value("Ana"));
    }
}