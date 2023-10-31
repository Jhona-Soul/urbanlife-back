package com.urbanlife.urbanlife.models.request;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Data
@Builder
public class BusquedaRequest {
    private String nombre;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
}
