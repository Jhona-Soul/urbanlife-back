package com.urbanlife.urbanlife.models.usuario;

import com.urbanlife.urbanlife.models.Productos;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
@Data
@Builder
@Entity
@Table
@AllArgsConstructor
public class ubicacion {
    @Id
    @SequenceGenerator(name="ubicacion_sequence", sequenceName = "ubicacion_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="ubicacion")
    private Integer idUbicacion;
    private String localidad;
    private String calle;
    private int altura;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "idUsuario")
    private Usuario usuario;
}









