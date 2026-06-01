package com.example.ms_gestion_taller.controller;

import com.example.ms_gestion_taller.dto.InscripcionResponse;
import com.example.ms_gestion_taller.service.TallerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/alumnos")
@RequiredArgsConstructor
public class AlumnoTallerController {

    private final TallerService service;

    @GetMapping("/{alumnoId}/talleres")
    public ResponseEntity<List<InscripcionResponse>> getTalleresDeAlumno(@PathVariable("alumnoId") Long alumnoId) {
        return ResponseEntity.ok(service.getInscripcionesByAlumno(alumnoId));
    }
}
