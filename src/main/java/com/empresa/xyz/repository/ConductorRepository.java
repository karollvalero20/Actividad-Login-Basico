package com.empresa.xyz.repository;

import com.empresa.xyz.model.Conductor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConductorRepository extends JpaRepository<Conductor, Long> {
    boolean existsByDocumento(String documento);
}
