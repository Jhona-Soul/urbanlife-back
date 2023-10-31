package com.urbanlife.urbanlife.models;

import com.urbanlife.urbanlife.models.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Favoritos {
    @Id
    @SequenceGenerator(name = "favoritos_sequence", sequenceName = "favoritos_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "favoritos")
    private Integer idFavoritos;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "idUsuario")
    private Usuario usuario;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "idProducto")
    private Productos productos;

}
