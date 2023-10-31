package com.urbanlife.urbanlife.models.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioResponse {
    private String nombre;
    private String apellido;
    private String email;
    private Integer telefono;
    private String urlImagen;


}
