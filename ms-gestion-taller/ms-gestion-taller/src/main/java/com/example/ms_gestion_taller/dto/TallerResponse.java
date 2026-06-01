package com.example.ms_gestion_taller.dto;

public record TallerResponse(
        Long id,
        String nombre,
        String descripcion,
        Integer capacidad,
        Long instructorId,
        String nombreInstructor,
        String apellidoInstructor
) {}
