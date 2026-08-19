package com.empresa.xyz.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "camiones")
@Getter
@Setter
@NoArgsConstructor
public class Camion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "La placa es obligatoria")
    @Column(unique = true)
    private String placa;

    @NotBlank(message = "El tipo de vehiculo es obligatorio")
    private String tipoVehiculo;

    @ManyToOne
    @JoinColumn(name = "conductor_id")
    private Conductor conductor;

    public Camion(String placa, String tipoVehiculo) {
        this.placa = placa;
        this.tipoVehiculo = tipoVehiculo;
    }
}
