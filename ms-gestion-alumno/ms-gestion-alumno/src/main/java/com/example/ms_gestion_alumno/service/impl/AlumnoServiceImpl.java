package com.example.ms_gestion_alumno.service.impl;

import com.example.ms_gestion_alumno.client.InstructorClientFacade;
import com.example.ms_gestion_alumno.dto.AlumnoRequest;
import com.example.ms_gestion_alumno.dto.AlumnoResponse;
import com.example.ms_gestion_alumno.dto.InstructorDTO;
import com.example.ms_gestion_alumno.exception.ResourceNotFoundException;
import com.example.ms_gestion_alumno.model.Alumno;
import com.example.ms_gestion_alumno.repository.AlumnoRepository;
import com.example.ms_gestion_alumno.service.AlumnoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AlumnoServiceImpl implements AlumnoService {

    private final AlumnoRepository repository;
    private final InstructorClientFacade instructorClientFacade;

    @Override
    @Transactional
    public AlumnoResponse createAlumno(AlumnoRequest request) {
        if (repository.existsByEmail(request.email())) {
            throw new DataIntegrityViolationException("El email ya está registrado");
        }

        InstructorDTO instructor = instructorClientFacade.getInstructorById(request.instructorId());
        if (instructor == null || instructor.id() == null) {
            throw new ResourceNotFoundException("Instructor no encontrado con id: " + request.instructorId());
        }

        Alumno alumno = Alumno.builder()
                .nombre(request.nombre())
                .email(request.email())
                .instructorId(request.instructorId())
                .build();

        Alumno saved = repository.save(alumno);
        return toResponse(saved, instructor.nombre());
    }

    @Override
    public AlumnoResponse getAlumnoById(Long id) {
        Alumno alumno = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Alumno no encontrado con id: " + id));

        InstructorDTO instructor = instructorClientFacade.getInstructorById(alumno.getInstructorId());
        String nombreInstructor = (instructor != null && instructor.id() != null)
                ? instructor.nombre()
                : "Instructor no disponible";

        return toResponse(alumno, nombreInstructor);
    }

    @Override
    public List<AlumnoResponse> getAllAlumnos() {
        return repository.findAll().stream()
                .map(alumno -> toResponse(alumno, "Información básica"))
                .collect(Collectors.toList());
    }

    @Override
    public List<AlumnoResponse> getAlumnosByInstructor(Long instructorId) {
        return repository.findByInstructorId(instructorId).stream()
                .map(alumno -> toResponse(alumno, "Información básica"))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public AlumnoResponse updateAlumno(Long id, AlumnoRequest request) {
        Alumno alumno = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Alumno no encontrado con id: " + id));

        if (!alumno.getEmail().equals(request.email()) && repository.existsByEmail(request.email())) {
            throw new DataIntegrityViolationException("El email ya está registrado");
        }

        InstructorDTO instructor = instructorClientFacade.getInstructorById(request.instructorId());
        if (instructor == null || instructor.id() == null) {
            throw new ResourceNotFoundException("Instructor no encontrado con id: " + request.instructorId());
        }

        alumno.setNombre(request.nombre());
        alumno.setEmail(request.email());
        alumno.setInstructorId(request.instructorId());

        return toResponse(repository.save(alumno), instructor.nombre());
    }

    @Override
    @Transactional
    public void deleteAlumno(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Alumno no encontrado con id: " + id);
        }
        repository.deleteById(id);
    }

    private AlumnoResponse toResponse(Alumno alumno, String nombreInstructor) {
        return new AlumnoResponse(
                alumno.getId(),
                alumno.getNombre(),
                alumno.getEmail(),
                alumno.getFechaInscripcion(),
                alumno.getEstado(),
                alumno.getInstructorId(),
                nombreInstructor
        );
    }
}
