package com.urbanlife.urbanlife.models.request;

import com.urbanlife.urbanlife.models.usuario.RolUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String nombre;
    private String apellido;
    private String email;
    private String password;
    private Integer telefono;
    private RolUser role;
}
