package com.example.ms_gestion_instructor.dto;

import java.time.LocalDateTime;

public record InstructorResponse(
        Long id,
        String nombre,
        String email,
        String especialidad,
        LocalDateTime fechaCreacion
) {}
