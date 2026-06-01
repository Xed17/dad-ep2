package com.example.ms_gestion_taller.controller;

import com.example.ms_gestion_taller.dto.TallerRequest;
import com.example.ms_gestion_taller.dto.TallerResponse;
import com.example.ms_gestion_taller.service.TallerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/talleres")
@RequiredArgsConstructor
public class TallerController {

    private final TallerService service;

    @GetMapping
    public ResponseEntity<List<TallerResponse>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TallerResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<TallerResponse> save(@Valid @RequestBody TallerRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TallerResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody TallerRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
