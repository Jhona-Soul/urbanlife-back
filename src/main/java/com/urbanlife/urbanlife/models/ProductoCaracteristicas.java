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
public class ProductoCaracteristicas {
    @Id
    @SequenceGenerator(name = "productoCaracteristica_sequence", sequenceName = "productoCaracteristica_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "productoCaracteristica")

    private Integer idProductoCaracteristica;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "idProducto")
    private Productos productos;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "idMedida")
    private SubCaracteristicas subCaracteristicas;


}
