package com.urbanlife.urbanlife.models.response;

import com.urbanlife.urbanlife.models.Categorias;
import com.urbanlife.urbanlife.models.Dto.ImagenDto;
import com.urbanlife.urbanlife.models.Medidas;
import jakarta.persistence.JoinColumn;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
public class ProductoResponse {
    private Integer idProducto;
    private String nombre;
    private double precio;
    private String detalle;
    private String color;
    private LocalDate fechaActual;
    @JoinColumn(name = "idCategoria")
    private Categorias categorias;
    private List<Medidas> talles;
    private Collection<ImagenDto> imagenes;
}
