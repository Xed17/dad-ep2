package com.example.ms_gestion_taller.client;

import com.example.ms_gestion_taller.dto.AlumnoDTO;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AlumnoClientFacade {

    private final AlumnoClient alumnoClient;

    @CircuitBreaker(name = "alumno-cb", fallbackMethod = "alumnoFallback")
    public AlumnoDTO getAlumnoById(Long alumnoId) {
        try {
            return alumnoClient.getAlumnoById(alumnoId);
        } catch (FeignException.NotFound e) {
            return null;
        }
    }

    public AlumnoDTO alumnoFallback(Long alumnoId, Throwable t) {
        log.error("Circuit Breaker activado - Alumno Service no disponible: {}", t.getMessage());
        return new AlumnoDTO(alumnoId, "Alumno no disponible", null, null);
    }
}
