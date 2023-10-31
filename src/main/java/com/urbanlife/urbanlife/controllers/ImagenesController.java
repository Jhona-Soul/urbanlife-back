package com.urbanlife.urbanlife.controllers;


import com.urbanlife.urbanlife.models.Dto.ImagenDto;
import com.urbanlife.urbanlife.models.ImagenesProducto;
import com.urbanlife.urbanlife.services.impl.IImagenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/imagenes")
public class ImagenesController {

    @Autowired
    IImagenService imagenService;

    @PostMapping("/registrar")
    public ResponseEntity<?>guardarImagen(@RequestBody ImagenesProducto imagenes) {
        imagenService.createImagen(imagenes);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/listarimagenes-all")
    public  ResponseEntity<Collection<ImagenesProducto>>getAllImagenes(){
        return ResponseEntity.ok(imagenService.getAllImagenes());
    }

    @GetMapping("/obtener/{id}")
    public ResponseEntity<Collection<ImagenDto>> listarImagenesPorProducto(@PathVariable Integer id) {
        Collection<ImagenDto> imagenesDelProducto = imagenService.listarImagenesPorProducto(id);
        return ResponseEntity.ok(imagenesDelProducto);
    }
    @GetMapping(
            value = "{id}/producto-image",
            produces = MediaType.IMAGE_PNG_VALUE
    )
    public byte[] getCategoryImage(
            @PathVariable("id") Integer id) {
        return imagenService.getProductoImagen(id);
    }

}
