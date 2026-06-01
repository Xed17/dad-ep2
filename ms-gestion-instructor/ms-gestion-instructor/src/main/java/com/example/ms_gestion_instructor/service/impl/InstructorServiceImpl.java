package com.example.ms_gestion_instructor.service.impl;

import com.example.ms_gestion_instructor.dto.InstructorRequest;
import com.example.ms_gestion_instructor.dto.InstructorResponse;
import com.example.ms_gestion_instructor.dto.InstructorUpdateRequest;
import com.example.ms_gestion_instructor.exception.ResourceNotFoundException;
import com.example.ms_gestion_instructor.model.Instructor;
import com.example.ms_gestion_instructor.repository.InstructorRepository;
import com.example.ms_gestion_instructor.service.InstructorService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InstructorServiceImpl implements InstructorService {

    private final InstructorRepository repository;

    @Override
    @Transactional
    public InstructorResponse createInstructor(InstructorRequest request) {
        if (repository.existsByEmail(request.email())) {
            throw new DataIntegrityViolationException("El email ya está registrado");
        }

        Instructor instructor = Instructor.builder()
                .nombre(request.nombre())
                .email(request.email())
                .especialidad(request.especialidad())
                .build();

        return toResponse(repository.save(instructor));
    }

    @Override
    public InstructorResponse getInstructorById(Long id) {
        return repository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Instructor no encontrado con id: " + id));
    }

    @Override
    public List<InstructorResponse> getAllInstructors() {
        return repository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public InstructorResponse updateInstructor(Long id, InstructorUpdateRequest request) {
        Instructor instructor = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Instructor no encontrado con id: " + id));

        if (request.nombre() != null) {
            instructor.setNombre(request.nombre());
        }
        if (request.email() != null && !request.email().equals(instructor.getEmail())) {
            if (repository.existsByEmail(request.email())) {
                throw new DataIntegrityViolationException("El email ya está registrado");
            }
            instructor.setEmail(request.email());
        }
        if (request.especialidad() != null) {
            instructor.setEspecialidad(request.especialidad());
        }

        return toResponse(repository.save(instructor));
    }

    @Override
    @Transactional
    public void deleteInstructor(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Instructor no encontrado con id: " + id);
        }
        repository.deleteById(id);
    }

    private InstructorResponse toResponse(Instructor instructor) {
        return new InstructorResponse(
                instructor.getId(),
                instructor.getNombre(),
                instructor.getEmail(),
                instructor.getEspecialidad(),
                instructor.getFechaCreacion()
        );
    }
}
