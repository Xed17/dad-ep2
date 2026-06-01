package com.example.ms_gestion_taller.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "talleres")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Taller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String nombre;

    @Column
    private String descripcion;

    @Positive
    @Column(nullable = false)
    private Integer capacidad;

    @Column(nullable = false)
    private Long instructorId;
}
