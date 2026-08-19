package com.empresa.xyz.controller;

import com.empresa.xyz.model.Camion;
import com.empresa.xyz.model.Conductor;
import com.empresa.xyz.repository.CamionRepository;
import com.empresa.xyz.repository.ConductorRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/camiones")
public class CamionController {

    private final CamionRepository camionRepository;
    private final ConductorRepository conductorRepository;

    public CamionController(CamionRepository camionRepository, ConductorRepository conductorRepository) {
        this.camionRepository = camionRepository;
        this.conductorRepository = conductorRepository;
    }

    @PostMapping
    public ResponseEntity<?> registrar(@Valid @RequestBody Camion camion) {
        if (camionRepository.existsByPlaca(camion.getPlaca())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", "Ya existe un camion con esa placa"));
        }
        Camion guardado = camionRepository.save(camion);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

    @GetMapping
    public List<Camion> listar() {
        return camionRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtener(@PathVariable Long id) {
        return camionRepository.findById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Camion no encontrado")));
    }

    @PutMapping("/{camionId}/asociar-conductor/{conductorId}")
    public ResponseEntity<?> asociarConductor(@PathVariable Long camionId, @PathVariable Long conductorId) {
        Camion camion = camionRepository.findById(camionId).orElse(null);
        if (camion == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Camion no encontrado"));
        }

        Conductor conductor = conductorRepository.findById(conductorId).orElse(null);
        if (conductor == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Conductor no encontrado"));
        }

        camion.setConductor(conductor);
        Camion actualizado = camionRepository.save(camion);
        return ResponseEntity.ok(actualizado);
    }
}
