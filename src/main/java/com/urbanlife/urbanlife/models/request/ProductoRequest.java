package com.urbanlife.urbanlife.models.request;

import com.urbanlife.urbanlife.models.Categorias;
import com.urbanlife.urbanlife.models.Medidas;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductoRequest {
    private String nombre;
    private Double precio;
    private String detalle;
    private String color;

    private Categorias categorias;
    private Collection<Medidas> talles;
}
