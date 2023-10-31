package com.urbanlife.urbanlife.controllers.auth;

import com.urbanlife.urbanlife.models.request.BusquedaRequest;
import com.urbanlife.urbanlife.services.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/home")
@RequiredArgsConstructor
public class SearchController {
    private final ProductoService productoService;
    @PostMapping("/search")
    public ResponseEntity<?> busquedaProducto(@RequestBody BusquedaRequest request) {
        ResponseEntity<?> response = null;
        if (request.getNombre() != null || (request.getFechaInicio() != null && request.getFechaFin() != null) )
            response = ResponseEntity.ok(productoService.listaProductosBusqueda(request));
        else
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return response;
    }
}
