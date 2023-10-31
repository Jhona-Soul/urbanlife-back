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
public class EventoProducto {
    @Id
    @SequenceGenerator(name = "eventoProducto_sequence", sequenceName = "eventoProducto_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "eventoProductoa")

    private Integer idEventoProducto;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "idEvento")
    private Eventos evento;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "idProducto")
    private Productos productos;
}
