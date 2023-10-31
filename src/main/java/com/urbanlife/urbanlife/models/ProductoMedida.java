package com.urbanlife.urbanlife.models;
import jakarta.persistence.*;
import lombok.*;

@Table
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoMedida {
    @Id
    @SequenceGenerator(name = "productoMedida_sequence", sequenceName = "productoMedida_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "productoMedida")
    private int idProductoMedida;
    private int cantidad;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "idProducto")
    private Productos productos;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "idMedida")
    private Medidas medida;
}
