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
public class SubCaracteristicas {
    @Id
    @SequenceGenerator(name="subCaracteristica_sequence", sequenceName = "subCaracteristica_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator ="subCaracteristica")

    private Integer idSubCaracteristica;
    private String tipo;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "idCaracteristica")
    private Caracteristicas caracteristica;
}
