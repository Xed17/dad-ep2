package com.example.ms_gestion_alumno.repository;

import com.example.ms_gestion_alumno.model.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlumnoRepository extends JpaRepository<Alumno, Long> {
    Optional<Alumno> findByEmail(String email);
    List<Alumno> findByInstructorId(Long instructorId);
    boolean existsByEmail(String email);
}
