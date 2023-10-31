package com.urbanlife.urbanlife.controllers;

import com.urbanlife.urbanlife.models.Medidas;
import com.urbanlife.urbanlife.models.ProductoMedida;
import com.urbanlife.urbanlife.services.impl.IProductoMediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/productomedida")
public class ProductoMediaController {
    @Autowired
    IProductoMediaService productoMediaService;
    @GetMapping("listarproductomedida-all")
    public ResponseEntity<Collection<ProductoMedida>> listarProductoMedida() {
        return ResponseEntity.ok(productoMediaService.obtenerListaProductoMedida());
    }
}
