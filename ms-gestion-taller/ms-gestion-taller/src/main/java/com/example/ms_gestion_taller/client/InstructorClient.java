package com.example.ms_gestion_taller.client;

import com.example.ms_gestion_taller.dto.InstructorDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-gestion-instructor")
public interface InstructorClient {

    @GetMapping("/api/instructores/{id}")
    InstructorDTO getInstructorById(@PathVariable("id") Long id);
}
