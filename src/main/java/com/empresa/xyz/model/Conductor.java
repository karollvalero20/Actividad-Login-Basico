package com.empresa.xyz.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "conductores")
@Getter
@Setter
@NoArgsConstructor
public class Conductor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El documento es obligatorio")
    @Column(unique = true)
    private String documento;

    @OneToMany(mappedBy = "conductor")
    @JsonIgnore
    private List<Camion> camiones = new ArrayList<>();

    public Conductor(String nombre, String documento) {
        this.nombre = nombre;
        this.documento = documento;
    }
}
