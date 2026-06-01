package com.example.ms_gestion_taller.controller;

import com.example.ms_gestion_taller.dto.InscripcionRequest;
import com.example.ms_gestion_taller.dto.InscripcionResponse;
import com.example.ms_gestion_taller.dto.TallerRequest;
import com.example.ms_gestion_taller.dto.TallerResponse;
import com.example.ms_gestion_taller.service.TallerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/talleres")
@RequiredArgsConstructor
public class TallerController {

    private final TallerService service;

    @PostMapping
    public ResponseEntity<TallerResponse> createTaller(@Valid @RequestBody TallerRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createTaller(request));
    }

    @GetMapping
    public ResponseEntity<List<TallerResponse>> getAllTalleres() {
        return ResponseEntity.ok(service.getAllTalleres());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TallerResponse> getTallerById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getTallerById(id));
    }

    @GetMapping("/instructor/{instructorId}")
    public ResponseEntity<List<TallerResponse>> getTalleresByInstructor(@PathVariable Long instructorId) {
        return ResponseEntity.ok(service.getTalleresByInstructor(instructorId));
    }

    @GetMapping("/fecha")
    public ResponseEntity<List<TallerResponse>> getTalleresByFechaRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return ResponseEntity.ok(service.getTalleresByFechaRange(start, end));
    }

    @PostMapping("/inscripciones")
    public ResponseEntity<InscripcionResponse> inscribirAlumno(@Valid @RequestBody InscripcionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.inscribirAlumno(request));
    }

    @DeleteMapping("/inscripciones/{tallerId}/alumnos/{alumnoId}")
    public ResponseEntity<Void> cancelarInscripcion(@PathVariable Long tallerId, @PathVariable Long alumnoId) {
        service.cancelarInscripcion(tallerId, alumnoId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{tallerId}/inscripciones")
    public ResponseEntity<List<InscripcionResponse>> getInscripcionesByTaller(@PathVariable Long tallerId) {
        return ResponseEntity.ok(service.getInscripcionesByTaller(tallerId));
    }
}
