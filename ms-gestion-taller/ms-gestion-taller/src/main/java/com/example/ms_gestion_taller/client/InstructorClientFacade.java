package com.example.ms_gestion_taller.client;

import com.example.ms_gestion_taller.dto.InstructorDTO;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class InstructorClientFacade {

    private final InstructorClient instructorClient;

    @CircuitBreaker(name = "instructor-cb", fallbackMethod = "instructorFallback")
    public InstructorDTO getInstructorById(Long instructorId) {
        try {
            return instructorClient.getInstructorById(instructorId);
        } catch (FeignException.NotFound e) {
            return null;
        }
    }

    public InstructorDTO instructorFallback(Long instructorId, Throwable t) {
        log.error("Circuit Breaker activado - Instructor Service no disponible: {}", t.getMessage());
        return new InstructorDTO(instructorId, "Instructor no disponible", null, null);
    }
}
