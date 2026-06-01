package com.example.ms_gestion_alumno.service;

import com.example.ms_gestion_alumno.dto.AlumnoRequest;
import com.example.ms_gestion_alumno.dto.AlumnoResponse;

import java.util.List;

public interface AlumnoService {
    AlumnoResponse createAlumno(AlumnoRequest request);
    AlumnoResponse getAlumnoById(Long id);
    List<AlumnoResponse> getAllAlumnos();
    List<AlumnoResponse> getAlumnosByInstructor(Long instructorId);
    AlumnoResponse updateAlumno(Long id, AlumnoRequest request);
    void deleteAlumno(Long id);
}
