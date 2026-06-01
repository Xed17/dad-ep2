package com.example.ms_gestion_alumno.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AlumnoRequest(
        @NotBlank(message = "El nombre es obligatorio")
        String nombre,

        @NotBlank(message = "El apellido es obligatorio")
        String apellido,

        @NotBlank(message = "El DNI es obligatorio")
        String dni,

        @Email(message = "El email debe ser válido")
        @NotBlank(message = "El email es obligatorio")
        String email
) {}
