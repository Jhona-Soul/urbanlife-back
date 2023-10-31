package com.urbanlife.urbanlife.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.net.URL;
import java.util.Set;


@Entity
@Table
@Data
@AllArgsConstructor
public class Categorias {
    @Id
    @SequenceGenerator(name="categorias_sequence", sequenceName = "categorias_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "categorias_sequence")

    private Integer idCategoria;
    private String titulo;
    private String descripcion;
    private String URLIMAGEN;
    private Boolean eliminarCategoria;

    @OneToMany(mappedBy = "categorias", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Productos> Productos;

    public Categorias() {
        URLIMAGEN = "";
        eliminarCategoria = false;;
    }
}
