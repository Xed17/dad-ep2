package com.example.ms_gestion_taller.repository;

import com.example.ms_gestion_taller.model.TallerAlumno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TallerAlumnoRepository extends JpaRepository<TallerAlumno, Long> {
    Optional<TallerAlumno> findByTallerIdAndAlumnoId(Long tallerId, Long alumnoId);
    List<TallerAlumno> findByAlumnoId(Long alumnoId);
    List<TallerAlumno> findByTallerId(Long tallerId);
}
