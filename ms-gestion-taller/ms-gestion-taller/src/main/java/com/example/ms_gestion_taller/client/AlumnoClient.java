package com.example.ms_gestion_taller.client;

import com.example.ms_gestion_taller.dto.AlumnoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "ms-gestion-alumno")
public interface AlumnoClient {

    @GetMapping("/api/alumnos/{id}")
    AlumnoDTO getAlumnoById(@PathVariable("id") Long id);

    @PutMapping("/api/alumnos/{id}/estado")
    void updateAlumnoEstado(@PathVariable("id") Long id, @RequestParam("estado") String estado);
}
