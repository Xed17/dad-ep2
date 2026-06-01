package com.example.ms_gestion_taller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record TallerRequest(
        @NotBlank(message = "El nombre es obligatorio")
        String nombre,

        String descripcion,

        @NotNull(message = "La capacidad es obligatoria")
        @Positive(message = "La capacidad debe ser un número positivo")
        Integer capacidad,

        @NotNull(message = "El id del instructor es obligatorio")
        Long instructorId
) {}
