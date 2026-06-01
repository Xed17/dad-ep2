package com.example.ms_gestion_instructor.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record InstructorUpdateRequest(
        @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
        String nombre,

        @Email(message = "Debe ser un email válido")
        String email,

        @Size(max = 100, message = "La especialidad no puede exceder 100 caracteres")
        String especialidad
) {}
