package com.urbanlife.urbanlife.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Table
@Entity
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class Caracteristicas {
    @Id
    @SequenceGenerator(name="caracteristica_sequence", sequenceName = "caracteristica_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="caracteristica")

    private Integer idCaracteristica;
    private String nombre;
}
