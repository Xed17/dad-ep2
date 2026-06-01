package com.example.ms_gestion_instructor.dto;

public record InstructorResponse(
        Long id,
        String nombre,
        String apellido,
        String especialidad,
        String email
) {}
