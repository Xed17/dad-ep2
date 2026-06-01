package com.example.ms_gestion_alumno.dto;

import java.time.LocalDateTime;

public record AlumnoResponse(
        Long id,
        String nombre,
        String email,
        LocalDateTime fechaInscripcion,
        String estado,
        Long instructorId,
        String nombreInstructor
) {}
