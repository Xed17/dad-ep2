package com.example.ms_gestion_instructor.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record InstructorRequest(
        @NotBlank(message = "El nombre es obligatorio")
        String nombre,

        @NotBlank(message = "El apellido es obligatorio")
        String apellido,

        @NotBlank(message = "La especialidad es obligatoria")
        String especialidad,

        @Email(message = "El email debe ser válido")
        @NotBlank(message = "El email es obligatorio")
        String email
) {}
