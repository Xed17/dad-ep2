package com.example.ms_gestion_alumno.repository;

import com.example.ms_gestion_alumno.model.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AlumnoRepository extends JpaRepository<Alumno, Long> {
    Optional<Alumno> findByDni(String dni);
    Optional<Alumno> findByEmail(String email);
    boolean existsByDni(String dni);
    boolean existsByEmail(String email);
}
