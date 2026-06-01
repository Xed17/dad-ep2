package com.example.ms_gestion_taller.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record TallerRequest(
        @NotBlank(message = "El nombre es obligatorio")
        String nombre,

        @NotBlank(message = "La descripción es obligatoria")
        String descripcion,

        @NotNull(message = "La fecha de inicio es obligatoria")
        LocalDateTime fechaInicio,

        @NotNull(message = "La fecha de fin es obligatoria")
        LocalDateTime fechaFin,

        @NotNull(message = "El cupo máximo es obligatorio")
        @Min(value = 1, message = "El cupo máximo debe ser al menos 1")
        Integer cupoMaximo,

        @NotNull(message = "El ID del instructor es obligatorio")
        Long instructorId
) {}
