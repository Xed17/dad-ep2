package com.example.ms_gestion_taller.dto;

public record InstructorDTO(
        Long id,
        String nombre,
        String apellido,
        String especialidad,
        String email
) {}
