package com.example.ms_gestion_taller.service;

import com.example.ms_gestion_taller.dto.TallerRequest;
import com.example.ms_gestion_taller.dto.TallerResponse;

import java.util.List;

public interface TallerService {
    List<TallerResponse> findAll();
    TallerResponse findById(Long id);
    TallerResponse save(TallerRequest request);
    TallerResponse update(Long id, TallerRequest request);
    void delete(Long id);
}
