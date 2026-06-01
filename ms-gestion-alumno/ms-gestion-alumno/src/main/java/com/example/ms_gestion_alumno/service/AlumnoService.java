package com.example.ms_gestion_alumno.service;

import com.example.ms_gestion_alumno.dto.AlumnoRequest;
import com.example.ms_gestion_alumno.dto.AlumnoResponse;

import java.util.List;

public interface AlumnoService {
    List<AlumnoResponse> findAll();
    AlumnoResponse findById(Long id);
    AlumnoResponse save(AlumnoRequest request);
    AlumnoResponse update(Long id, AlumnoRequest request);
    void delete(Long id);
}
