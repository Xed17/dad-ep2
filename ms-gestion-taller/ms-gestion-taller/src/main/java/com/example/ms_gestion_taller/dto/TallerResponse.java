package com.example.ms_gestion_taller.dto;

import java.time.LocalDateTime;

public record TallerResponse(
        Long id,
        String nombre,
        String descripcion,
        LocalDateTime fechaInicio,
        LocalDateTime fechaFin,
        Integer cupoMaximo,
        Integer cupoDisponible,
        Long instructorId,
        String nombreInstructor
) {}
