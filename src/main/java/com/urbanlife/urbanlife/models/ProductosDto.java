package com.urbanlife.urbanlife.models;

import jakarta.persistence.JoinColumn;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductosDto {
    private Integer idProducto;
    private String nombre;
    private double precio;
    private String detalle;
    private String color;
    private String tela;
    private String genero;
    private String corte;
    private String temporada;
    @JoinColumn(name = "idCategoria")
    private Categorias categorias;
}
