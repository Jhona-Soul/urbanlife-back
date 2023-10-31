package com.urbanlife.urbanlife.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Table
@Entity
@Data
@AllArgsConstructor
@Builder
public class Productos {
    @Id
    @SequenceGenerator(name="productos_sequence", sequenceName = "productos_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "productos_sequence")

    private Integer idProducto;
    private String nombre;
    private Double precio;
    @Column(
            nullable = false
    )
    private String detalle;
    @Column(
            nullable = false
    )
    private String color;
    private LocalDate fechaActual;
    private Boolean eliminarProducto;
    private double puntuacion;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "idCategoria")
    private Categorias categorias;

    @OneToMany(mappedBy = "productos", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<ImagenesProducto> ImagenesProducto;
    public Productos() {
        fechaActual = LocalDate.now();
        eliminarProducto = false;
        puntuacion = 0.0;
    }
}
