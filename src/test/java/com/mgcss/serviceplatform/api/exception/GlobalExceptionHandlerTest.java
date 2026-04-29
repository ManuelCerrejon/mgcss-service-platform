package com.mgcss.serviceplatform.api.exception;

import com.mgcss.serviceplatform.domain.SolicitudNoEncontradaException;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void deberiaManejarSolicitudNoEncontrada() {
        SolicitudNoEncontradaException ex =
                new SolicitudNoEncontradaException("No encontrada");

        ResponseEntity<Map<String, String>> response =
                handler.handleNotFound(ex);

        assertEquals(404, response.getStatusCode().value());
        assertEquals("No encontrada", response.getBody().get("error"));
    }

    @Test
    void deberiaManejarIllegalArgument() {
        IllegalArgumentException ex =
                new IllegalArgumentException("Error argumento");

        ResponseEntity<Map<String, String>> response =
                handler.handleBadRequest(ex);

        assertEquals(400, response.getStatusCode().value());
        assertEquals("Error argumento", response.getBody().get("error"));
    }

    @Test
    void deberiaManejarIllegalState() {
        IllegalStateException ex =
                new IllegalStateException("Error estado");

        ResponseEntity<Map<String, String>> response =
                handler.handleConflict(ex);

        assertEquals(409, response.getStatusCode().value());
        assertEquals("Error estado", response.getBody().get("error"));
    }
    
    @Test
    void deberiaManejarErroresDeValidacion() {
        MethodArgumentNotValidException ex = 
                org.mockito.Mockito.mock(MethodArgumentNotValidException.class);

        org.springframework.validation.BindingResult bindingResult =
                org.mockito.Mockito.mock(org.springframework.validation.BindingResult.class);

        org.springframework.validation.FieldError fieldError =
                new org.springframework.validation.FieldError("obj", "descripcion", "error");

        org.mockito.Mockito.when(ex.getBindingResult()).thenReturn(bindingResult);
        org.mockito.Mockito.when(bindingResult.getFieldErrors())
                .thenReturn(java.util.List.of(fieldError));

        ResponseEntity<Map<String, String>> response =
                handler.handleValidationErrors(ex);

        assertEquals(400, response.getStatusCode().value());
        assertEquals("error", response.getBody().get("descripcion"));
    }
}