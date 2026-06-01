package com.example.ms_gestion_instructor.controller;

import com.example.ms_gestion_instructor.dto.InstructorRequest;
import com.example.ms_gestion_instructor.dto.InstructorResponse;
import com.example.ms_gestion_instructor.service.InstructorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/instructores")
@RequiredArgsConstructor
public class InstructorController {

    private final InstructorService service;

    @GetMapping
    public ResponseEntity<List<InstructorResponse>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InstructorResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<InstructorResponse> save(@Valid @RequestBody InstructorRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InstructorResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody InstructorRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
