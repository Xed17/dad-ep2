package com.example.ms_gestion_taller.repository;

import com.example.ms_gestion_taller.model.Taller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TallerRepository extends JpaRepository<Taller, Long> {
    List<Taller> findByInstructorId(Long instructorId);
    List<Taller> findByFechaInicioBetween(LocalDateTime start, LocalDateTime end);
}
