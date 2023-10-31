package com.urbanlife.urbanlife.controllers;

import com.urbanlife.urbanlife.models.Medidas;
import com.urbanlife.urbanlife.services.impl.IMedidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/talles")
public class MedidaController {
    @Autowired
    IMedidaService medidaService;

    @PostMapping("/registrarTalle")
    public ResponseEntity<?> registrarTalle(@RequestBody Medidas medida) {
        medidaService.createMedida(medida);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/listartalles-all")
    public ResponseEntity<Collection<Medidas>> listartalles() {
        return ResponseEntity.ok(medidaService.getAllMedidas());
    }
    @GetMapping("/listatalles-producto/{id}")
    public ResponseEntity<List<Medidas>> listaTallesProducto(@PathVariable Integer id) {
        return ResponseEntity.ok(medidaService.listarTallesProducto(id));
    }
}
