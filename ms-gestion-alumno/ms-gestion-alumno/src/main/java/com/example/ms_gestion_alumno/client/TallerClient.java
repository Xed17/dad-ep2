package com.example.ms_gestion_alumno.client;

import com.example.ms_gestion_alumno.dto.TallerDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "ms-gestion-taller")
public interface TallerClient {

    @GetMapping("/api/talleres/alumno/{alumnoId}")
    List<TallerDTO> getTalleresByAlumno(@PathVariable("alumnoId") Long alumnoId);
}
