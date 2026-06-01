package com.example.ms_gestion_taller.service.impl;

import com.example.ms_gestion_taller.client.AlumnoClientFacade;
import com.example.ms_gestion_taller.client.InstructorClientFacade;
import com.example.ms_gestion_taller.dto.*;
import com.example.ms_gestion_taller.exception.ResourceNotFoundException;
import com.example.ms_gestion_taller.model.Taller;
import com.example.ms_gestion_taller.model.TallerAlumno;
import com.example.ms_gestion_taller.repository.TallerAlumnoRepository;
import com.example.ms_gestion_taller.repository.TallerRepository;
import com.example.ms_gestion_taller.service.TallerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TallerServiceImpl implements TallerService {

    private final TallerRepository tallerRepository;
    private final TallerAlumnoRepository tallerAlumnoRepository;
    private final InstructorClientFacade instructorClientFacade;
    private final AlumnoClientFacade alumnoClientFacade;

    @Override
    @Transactional
    public TallerResponse createTaller(TallerRequest request) {
        if (request.fechaInicio().isAfter(request.fechaFin()) || request.fechaInicio().isEqual(request.fechaFin())) {
            throw new IllegalArgumentException("La fecha de inicio debe ser anterior a la fecha de fin");
        }

        InstructorDTO instructor = instructorClientFacade.getInstructorById(request.instructorId());
        if (instructor == null || instructor.id() == null) {
            throw new ResourceNotFoundException("Instructor no encontrado con id: " + request.instructorId());
        }

        Taller taller = Taller.builder()
                .nombre(request.nombre())
                .descripcion(request.descripcion())
                .fechaInicio(request.fechaInicio())
                .fechaFin(request.fechaFin())
                .cupoMaximo(request.cupoMaximo())
                .cupoDisponible(request.cupoMaximo())
                .instructorId(request.instructorId())
                .build();

        Taller saved = tallerRepository.save(taller);
        return toTallerResponse(saved, instructor.nombre());
    }

    @Override
    public List<TallerResponse> getAllTalleres() {
        return tallerRepository.findAll().stream()
                .map(t -> toTallerResponse(t, "Información básica"))
                .collect(Collectors.toList());
    }

    @Override
    public TallerResponse getTallerById(Long id) {
        Taller taller = tallerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Taller no encontrado con id: " + id));

        InstructorDTO instructor = instructorClientFacade.getInstructorById(taller.getInstructorId());
        String nombreInstructor = (instructor != null && instructor.id() != null)
                ? instructor.nombre()
                : "Instructor no disponible";

        return toTallerResponse(taller, nombreInstructor);
    }

    @Override
    public List<TallerResponse> getTalleresByInstructor(Long instructorId) {
        return tallerRepository.findByInstructorId(instructorId).stream()
                .map(t -> toTallerResponse(t, "Información básica"))
                .collect(Collectors.toList());
    }

    @Override
    public List<TallerResponse> getTalleresByFechaRange(LocalDateTime start, LocalDateTime end) {
        return tallerRepository.findByFechaInicioBetween(start, end).stream()
                .map(t -> toTallerResponse(t, "Información básica"))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public InscripcionResponse inscribirAlumno(InscripcionRequest request) {
        Taller taller = tallerRepository.findById(request.tallerId())
                .orElseThrow(() -> new ResourceNotFoundException("Taller no encontrado con id: " + request.tallerId()));

        if (taller.getCupoDisponible() <= 0) {
            throw new IllegalArgumentException("No hay cupos disponibles para el taller seleccionado");
        }

        AlumnoDTO alumno = alumnoClientFacade.getAlumnoById(request.alumnoId());
        if (alumno == null || alumno.id() == null) {
            throw new ResourceNotFoundException("Alumno no encontrado con id: " + request.alumnoId());
        }

        tallerAlumnoRepository.findByTallerIdAndAlumnoId(request.tallerId(), request.alumnoId())
                .ifPresent(ta -> {
                    if ("INSCRITO".equals(ta.getEstado())) {
                        throw new DataIntegrityViolationException("El alumno ya está inscrito en este taller");
                    }
                });

        TallerAlumno inscripcion = TallerAlumno.builder()
                .tallerId(request.tallerId())
                .alumnoId(request.alumnoId())
                .build();

        // Optimistic Locking activo via @Version en Taller
        taller.setCupoDisponible(taller.getCupoDisponible() - 1);
        tallerRepository.save(taller);

        TallerAlumno saved = tallerAlumnoRepository.save(inscripcion);

        return new InscripcionResponse(
                saved.getId(),
                saved.getAlumnoId(),
                alumno.nombre(),
                saved.getTallerId(),
                taller.getNombre(),
                saved.getFechaInscripcion(),
                saved.getEstado()
        );
    }

    @Override
    @Transactional
    public void cancelarInscripcion(Long tallerId, Long alumnoId) {
        TallerAlumno inscripcion = tallerAlumnoRepository.findByTallerIdAndAlumnoId(tallerId, alumnoId)
                .orElseThrow(() -> new ResourceNotFoundException("Inscripción no encontrada para tallerId=" + tallerId + " alumnoId=" + alumnoId));

        if ("CANCELADO".equals(inscripcion.getEstado())) {
            throw new IllegalArgumentException("La inscripción ya se encuentra cancelada");
        }

        inscripcion.setEstado("CANCELADO");
        tallerAlumnoRepository.save(inscripcion);

        Taller taller = tallerRepository.findById(tallerId)
                .orElseThrow(() -> new ResourceNotFoundException("Taller no encontrado con id: " + tallerId));

        taller.setCupoDisponible(taller.getCupoDisponible() + 1);
        tallerRepository.save(taller);
    }

    @Override
    public List<InscripcionResponse> getInscripcionesByTaller(Long tallerId) {
        Taller taller = tallerRepository.findById(tallerId)
                .orElseThrow(() -> new ResourceNotFoundException("Taller no encontrado con id: " + tallerId));

        return tallerAlumnoRepository.findByTallerId(tallerId).stream()
                .map(insc -> new InscripcionResponse(
                        insc.getId(),
                        insc.getAlumnoId(),
                        "N/A",
                        insc.getTallerId(),
                        taller.getNombre(),
                        insc.getFechaInscripcion(),
                        insc.getEstado()
                )).collect(Collectors.toList());
    }

    @Override
    public List<InscripcionResponse> getInscripcionesByAlumno(Long alumnoId) {
        return tallerAlumnoRepository.findByAlumnoId(alumnoId).stream()
                .map(insc -> new InscripcionResponse(
                        insc.getId(),
                        insc.getAlumnoId(),
                        "N/A",
                        insc.getTallerId(),
                        "N/A",
                        insc.getFechaInscripcion(),
                        insc.getEstado()
                )).collect(Collectors.toList());
    }

    private TallerResponse toTallerResponse(Taller taller, String nombreInstructor) {
        return new TallerResponse(
                taller.getId(),
                taller.getNombre(),
                taller.getDescripcion(),
                taller.getFechaInicio(),
                taller.getFechaFin(),
                taller.getCupoMaximo(),
                taller.getCupoDisponible(),
                taller.getInstructorId(),
                nombreInstructor
        );
    }
}
