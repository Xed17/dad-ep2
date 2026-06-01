package com.example.ms_gestion_alumno.service.impl;

import com.example.ms_gestion_alumno.dto.AlumnoRequest;
import com.example.ms_gestion_alumno.dto.AlumnoResponse;
import com.example.ms_gestion_alumno.model.Alumno;
import com.example.ms_gestion_alumno.repository.AlumnoRepository;
import com.example.ms_gestion_alumno.service.AlumnoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AlumnoServiceImpl implements AlumnoService {

    private final AlumnoRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<AlumnoResponse> findAll() {
        return repository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public AlumnoResponse findById(Long id) {
        return repository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new RuntimeException("Alumno no encontrado con id: " + id));
    }

    @Override
    public AlumnoResponse save(AlumnoRequest request) {
        Alumno alumno = new Alumno();
        alumno.setNombre(request.nombre());
        alumno.setApellido(request.apellido());
        alumno.setDni(request.dni());
        alumno.setEmail(request.email());
        return toResponse(repository.save(alumno));
    }

    @Override
    public AlumnoResponse update(Long id, AlumnoRequest request) {
        Alumno alumno = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alumno no encontrado con id: " + id));
        alumno.setNombre(request.nombre());
        alumno.setApellido(request.apellido());
        alumno.setDni(request.dni());
        alumno.setEmail(request.email());
        return toResponse(repository.save(alumno));
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Alumno no encontrado con id: " + id);
        }
        repository.deleteById(id);
    }

    private AlumnoResponse toResponse(Alumno alumno) {
        return new AlumnoResponse(
                alumno.getId(),
                alumno.getNombre(),
                alumno.getApellido(),
                alumno.getDni(),
                alumno.getEmail()
        );
    }
}
