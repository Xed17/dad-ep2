package com.example.ms_gestion_taller.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "taller_alumno")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TallerAlumno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "taller_id", nullable = false)
    private Long tallerId;

    @Column(name = "alumno_id", nullable = false)
    private Long alumnoId;

    @Column(name = "fecha_inscripcion", nullable = false)
    private LocalDateTime fechaInscripcion;

    @Column(nullable = false)
    private String estado;

    @PrePersist
    protected void onCreate() {
        if (fechaInscripcion == null) {
            fechaInscripcion = LocalDateTime.now();
        }
        if (estado == null) {
            estado = "INSCRITO";
        }
    }
}
