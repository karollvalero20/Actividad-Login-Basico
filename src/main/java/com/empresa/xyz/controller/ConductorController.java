package com.empresa.xyz.controller;

import com.empresa.xyz.model.Conductor;
import com.empresa.xyz.repository.ConductorRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/conductores")
public class ConductorController {

    private final ConductorRepository conductorRepository;

    public ConductorController(ConductorRepository conductorRepository) {
        this.conductorRepository = conductorRepository;
    }

    @PostMapping
    public ResponseEntity<?> registrar(@Valid @RequestBody Conductor conductor) {
        if (conductorRepository.existsByDocumento(conductor.getDocumento())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", "Ya existe un conductor con ese documento"));
        }
        Conductor guardado = conductorRepository.save(conductor);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

    @GetMapping
    public List<Conductor> listar() {
        return conductorRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtener(@PathVariable Long id) {
        return conductorRepository.findById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Conductor no encontrado")));
    }
}
