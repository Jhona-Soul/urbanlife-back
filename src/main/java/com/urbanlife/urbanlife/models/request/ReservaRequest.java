package com.urbanlife.urbanlife.models.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservaRequest {
    private LocalDate fechaReserva;
    private LocalDate fechaIniciAlquiler;
    private LocalDate fechaFinAlquiler;
    private Integer idProducto;
    private Integer idUsuario;
}
