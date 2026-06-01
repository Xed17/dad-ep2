package com.example.ms_gestion_alumno.controller;

import com.example.ms_gestion_alumno.dto.AlumnoRequest;
import com.example.ms_gestion_alumno.dto.AlumnoResponse;
import com.example.ms_gestion_alumno.service.AlumnoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alumnos")
@RequiredArgsConstructor
public class AlumnoController {

    private final AlumnoService service;

    @PostMapping
    public ResponseEntity<AlumnoResponse> createAlumno(@Valid @RequestBody AlumnoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createAlumno(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlumnoResponse> getAlumnoById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getAlumnoById(id));
    }

    @GetMapping
    public ResponseEntity<List<AlumnoResponse>> getAllAlumnos() {
        return ResponseEntity.ok(service.getAllAlumnos());
    }

    @GetMapping("/instructor/{instructorId}")
    public ResponseEntity<List<AlumnoResponse>> getAlumnosByInstructor(@PathVariable Long instructorId) {
        return ResponseEntity.ok(service.getAlumnosByInstructor(instructorId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlumnoResponse> updateAlumno(
            @PathVariable Long id,
            @Valid @RequestBody AlumnoRequest request) {
        return ResponseEntity.ok(service.updateAlumno(id, request));
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<Void> updateAlumnoEstado(
            @PathVariable Long id,
            @RequestParam String estado) {
        service.updateAlumnoEstado(id, estado);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlumno(@PathVariable Long id) {
        service.deleteAlumno(id);
        return ResponseEntity.noContent().build();
    }
}
