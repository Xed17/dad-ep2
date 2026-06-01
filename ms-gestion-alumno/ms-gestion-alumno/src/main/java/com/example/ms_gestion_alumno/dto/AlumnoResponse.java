package com.example.ms_gestion_alumno.dto;

public record AlumnoResponse(
        Long id,
        String nombre,
        String apellido,
        String dni,
        String email
) {}
