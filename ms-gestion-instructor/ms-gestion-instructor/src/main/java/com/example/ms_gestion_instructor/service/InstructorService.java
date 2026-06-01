package com.example.ms_gestion_instructor.service;

import com.example.ms_gestion_instructor.dto.InstructorRequest;
import com.example.ms_gestion_instructor.dto.InstructorResponse;
import com.example.ms_gestion_instructor.dto.InstructorUpdateRequest;

import java.util.List;

public interface InstructorService {
    InstructorResponse createInstructor(InstructorRequest request);
    InstructorResponse getInstructorById(Long id);
    List<InstructorResponse> getAllInstructors();
    InstructorResponse updateInstructor(Long id, InstructorUpdateRequest request);
    void deleteInstructor(Long id);
}
