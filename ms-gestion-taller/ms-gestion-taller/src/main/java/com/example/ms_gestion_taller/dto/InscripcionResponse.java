package com.example.ms_gestion_taller.dto;

import java.time.LocalDateTime;

public record InscripcionResponse(
        Long id,
        Long alumnoId,
        String nombreAlumno,
        Long tallerId,
        String nombreTaller,
        LocalDateTime fechaInscripcion,
        String estado
) {}
