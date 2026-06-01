package com.example.ms_gestion_instructor.service.impl;

import com.example.ms_gestion_instructor.dto.InstructorRequest;
import com.example.ms_gestion_instructor.dto.InstructorResponse;
import com.example.ms_gestion_instructor.model.Instructor;
import com.example.ms_gestion_instructor.repository.InstructorRepository;
import com.example.ms_gestion_instructor.service.InstructorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class InstructorServiceImpl implements InstructorService {

    private final InstructorRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<InstructorResponse> findAll() {
        return repository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public InstructorResponse findById(Long id) {
        return repository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new RuntimeException("Instructor no encontrado con id: " + id));
    }

    @Override
    public InstructorResponse save(InstructorRequest request) {
        Instructor instructor = new Instructor();
        instructor.setNombre(request.nombre());
        instructor.setApellido(request.apellido());
        instructor.setEspecialidad(request.especialidad());
        instructor.setEmail(request.email());
        return toResponse(repository.save(instructor));
    }

    @Override
    public InstructorResponse update(Long id, InstructorRequest request) {
        Instructor instructor = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Instructor no encontrado con id: " + id));
        instructor.setNombre(request.nombre());
        instructor.setApellido(request.apellido());
        instructor.setEspecialidad(request.especialidad());
        instructor.setEmail(request.email());
        return toResponse(repository.save(instructor));
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Instructor no encontrado con id: " + id);
        }
        repository.deleteById(id);
    }

    private InstructorResponse toResponse(Instructor instructor) {
        return new InstructorResponse(
                instructor.getId(),
                instructor.getNombre(),
                instructor.getApellido(),
                instructor.getEspecialidad(),
                instructor.getEmail()
        );
    }
}
