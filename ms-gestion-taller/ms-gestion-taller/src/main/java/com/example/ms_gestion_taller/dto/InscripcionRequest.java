package com.example.ms_gestion_taller.dto;

import jakarta.validation.constraints.NotNull;

public record InscripcionRequest(
        @NotNull(message = "El ID del alumno es obligatorio")
        Long alumnoId,
        
        @NotNull(message = "El ID del taller es obligatorio")
        Long tallerId
) {}
