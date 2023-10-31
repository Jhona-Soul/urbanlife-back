package com.urbanlife.urbanlife.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Table
@Entity
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class Eventos {
    @Id
    @SequenceGenerator(name="evento_sequence", sequenceName = "evento_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator ="evento")
    private Integer idEvento;

    private String tipo;
    private LocalDate fechaInicioEvento;
    private LocalDate fechaFinEvento;
}
