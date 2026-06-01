package com.example.ms_gestion_instructor.repository;

import com.example.ms_gestion_instructor.model.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Long> {
    Optional<Instructor> findByEmail(String email);
    boolean existsByEmail(String email);
}
