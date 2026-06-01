package com.example.ms_gestion_taller.service.impl;

import com.example.ms_gestion_taller.client.InstructorClient;
import com.example.ms_gestion_taller.dto.InstructorDTO;
import com.example.ms_gestion_taller.dto.TallerRequest;
import com.example.ms_gestion_taller.dto.TallerResponse;
import com.example.ms_gestion_taller.model.Taller;
import com.example.ms_gestion_taller.repository.TallerRepository;
import com.example.ms_gestion_taller.service.TallerService;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class TallerServiceImpl implements TallerService {

    private final TallerRepository repository;
    private final InstructorClient instructorClient;

    @Override
    @Transactional(readOnly = true)
    public List<TallerResponse> findAll() {
        return repository.findAll().stream()
                .map(taller -> toResponse(taller, obtenerInstructor(taller.getInstructorId())))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public TallerResponse findById(Long id) {
        Taller taller = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Taller no encontrado con id: " + id));
        return toResponse(taller, obtenerInstructor(taller.getInstructorId()));
    }

    @Override
    @CircuitBreaker(name = "instructor-cb", fallbackMethod = "saveFallback")
    public TallerResponse save(TallerRequest request) {
        // Verifica que el instructor exista antes de guardar
        try {
            instructorClient.findById(request.instructorId());
        } catch (FeignException.NotFound e) {
            throw new RuntimeException("Instructor con id " + request.instructorId() + " no existe");
        }

        Taller taller = new Taller();
        taller.setNombre(request.nombre());
        taller.setDescripcion(request.descripcion());
        taller.setCapacidad(request.capacidad());
        taller.setInstructorId(request.instructorId());
        Taller saved = repository.save(taller);
        return toResponse(saved, obtenerInstructor(saved.getInstructorId()));
    }

    @Override
    @CircuitBreaker(name = "instructor-cb", fallbackMethod = "updateFallback")
    public TallerResponse update(Long id, TallerRequest request) {
        Taller taller = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Taller no encontrado con id: " + id));
        // Verifica que el instructor exista
        try {
            instructorClient.findById(request.instructorId());
        } catch (FeignException.NotFound e) {
            throw new RuntimeException("Instructor con id " + request.instructorId() + " no existe");
        }
        taller.setNombre(request.nombre());
        taller.setDescripcion(request.descripcion());
        taller.setCapacidad(request.capacidad());
        taller.setInstructorId(request.instructorId());
        return toResponse(repository.save(taller), obtenerInstructor(taller.getInstructorId()));
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Taller no encontrado con id: " + id);
        }
        repository.deleteById(id);
    }

    // ---------- Circuit Breaker Fallbacks ----------

    public TallerResponse saveFallback(TallerRequest request, Throwable t) {
        log.error("Circuit breaker activado al crear taller. Instructor service caído: {}", t.getMessage());
        throw new RuntimeException("El servicio de instructores no está disponible. Intente más tarde.");
    }

    public TallerResponse updateFallback(Long id, TallerRequest request, Throwable t) {
        log.error("Circuit breaker activado al actualizar taller. Instructor service caído: {}", t.getMessage());
        throw new RuntimeException("El servicio de instructores no está disponible. Intente más tarde.");
    }

    // ---------- Helper Methods ----------

    private InstructorDTO obtenerInstructor(Long instructorId) {
        try {
            return instructorClient.findById(instructorId);
        } catch (Exception e) {
            log.warn("No se pudo obtener datos del instructor {}: {}", instructorId, e.getMessage());
            return null;
        }
    }

    private TallerResponse toResponse(Taller taller, InstructorDTO instructor) {
        String nombre = instructor != null ? instructor.nombre() : "N/A";
        String apellido = instructor != null ? instructor.apellido() : "N/A";
        return new TallerResponse(
                taller.getId(),
                taller.getNombre(),
                taller.getDescripcion(),
                taller.getCapacidad(),
                taller.getInstructorId(),
                nombre,
                apellido
        );
    }
}
