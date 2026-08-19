package com.empresa.xyz.repository;

import com.empresa.xyz.model.Camion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CamionRepository extends JpaRepository<Camion, Long> {
    boolean existsByPlaca(String placa);
}
