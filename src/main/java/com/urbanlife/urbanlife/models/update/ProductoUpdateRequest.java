package com.urbanlife.urbanlife.models.update;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductoUpdateRequest {
    private String nombre;
    private double precio;
    private String detalle;
    private String color;
    private String tela;
    private String genero;
    private String evento;
    private String temporada;
    private Integer categorias;
}
