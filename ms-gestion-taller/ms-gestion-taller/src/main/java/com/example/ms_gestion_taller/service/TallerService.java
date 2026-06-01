package com.example.ms_gestion_taller.service;

import com.example.ms_gestion_taller.dto.InscripcionRequest;
import com.example.ms_gestion_taller.dto.InscripcionResponse;
import com.example.ms_gestion_taller.dto.TallerRequest;
import com.example.ms_gestion_taller.dto.TallerResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface TallerService {
    // Talleres
    TallerResponse createTaller(TallerRequest request);
    List<TallerResponse> getAllTalleres();
    TallerResponse getTallerById(Long id);
    List<TallerResponse> getTalleresByInstructor(Long instructorId);
    List<TallerResponse> getTalleresByFechaRange(LocalDateTime start, LocalDateTime end);

    // Inscripciones
    InscripcionResponse inscribirAlumno(InscripcionRequest request);
    void cancelarInscripcion(Long tallerId, Long alumnoId);
    List<InscripcionResponse> getInscripcionesByTaller(Long tallerId);
    List<InscripcionResponse> getInscripcionesByAlumno(Long alumnoId);
}
