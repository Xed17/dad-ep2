package com.example.ms_gestion_instructor.service;

import com.example.ms_gestion_instructor.dto.InstructorRequest;
import com.example.ms_gestion_instructor.dto.InstructorResponse;

import java.util.List;

public interface InstructorService {
    List<InstructorResponse> findAll();
    InstructorResponse findById(Long id);
    InstructorResponse save(InstructorRequest request);
    InstructorResponse update(Long id, InstructorRequest request);
    void delete(Long id);
}
