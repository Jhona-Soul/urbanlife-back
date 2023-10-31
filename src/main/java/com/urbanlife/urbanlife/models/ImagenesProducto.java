package com.urbanlife.urbanlife.models;
import jakarta.persistence.*;
import lombok.*;

@Table
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImagenesProducto {
    @Id
    @SequenceGenerator(name="imagen_sequence", sequenceName = "imagen_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator ="imagen")
    private Integer idImagen;
    private String urlImagen;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "idProducto")
    private Productos productos;

}
