package com.mgcss.serviceplatform.api;

import com.mgcss.serviceplatform.api.dto.AsignarTecnicoRequestDTO;
import com.mgcss.serviceplatform.api.dto.CambioEstadoRequestDTO;
import com.mgcss.serviceplatform.api.dto.SolicitudRequestDTO;
import com.mgcss.serviceplatform.api.dto.SolicitudResponseDTO;
import com.mgcss.serviceplatform.api.mapper.SolicitudMapper;
import com.mgcss.serviceplatform.domain.Solicitud;
import com.mgcss.serviceplatform.domain.Tecnico;
import com.mgcss.serviceplatform.service.SolicitudService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/solicitudes")
public class SolicitudController {

    private final SolicitudService solicitudService;

    public SolicitudController(SolicitudService solicitudService) {
        this.solicitudService = solicitudService;
    }

    @PostMapping
    public SolicitudResponseDTO crearSolicitud(@Valid @RequestBody SolicitudRequestDTO request) {
        Solicitud solicitud = solicitudService.crearSolicitudSiDescripcionValida(
                null,
                request.getDescripcion()
        );

        return SolicitudMapper.toResponseDTO(solicitud);
    }

    @GetMapping("/{id}")
    public SolicitudResponseDTO obtenerSolicitud(@PathVariable Long id) {
        Solicitud solicitud = solicitudService.obtenerSolicitud(id);
        return SolicitudMapper.toResponseDTO(solicitud);
    }

    @GetMapping
    public List<SolicitudResponseDTO> listarSolicitudes() {
        return solicitudService.listarSolicitudes()
                .stream()
                .map(SolicitudMapper::toResponseDTO)
                .toList();
    }

    @PutMapping("/{id}/estado")
    public SolicitudResponseDTO cambiarEstado(
            @PathVariable Long id,
            @Valid @RequestBody CambioEstadoRequestDTO request
    ) {
        Solicitud solicitud = solicitudService.cambiarEstado(id, request.getEstado());
        return SolicitudMapper.toResponseDTO(solicitud);
    }

    @PatchMapping("/{id}/reabrir")
    public SolicitudResponseDTO reabrirSolicitud(@PathVariable Long id) {
        Solicitud solicitud = solicitudService.reabrirSolicitud(id);
        return SolicitudMapper.toResponseDTO(solicitud);
    }

    @PutMapping("/{id}/tecnico")
    public SolicitudResponseDTO asignarTecnico(
            @PathVariable Long id,
            @Valid @RequestBody AsignarTecnicoRequestDTO request
    ) {
        Tecnico tecnico = new Tecnico(request.getNombre(), request.isActivo());
        Solicitud solicitud = solicitudService.asignarTecnico(id, tecnico);

        return SolicitudMapper.toResponseDTO(solicitud);
    }
}