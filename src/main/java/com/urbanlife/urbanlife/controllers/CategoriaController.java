package com.urbanlife.urbanlife.controllers;

import com.urbanlife.urbanlife.models.Categorias;
import com.urbanlife.urbanlife.models.Dto.CategoriaDto;
import com.urbanlife.urbanlife.models.update.CategoriaUpdateRequest;
import com.urbanlife.urbanlife.services.impl.ICategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {
    @Autowired
    ICategoriaService categoriaService;
    @PostMapping("/registrar")
    public ResponseEntity<String> registrarCategoria (
            @RequestBody CategoriaDto categoria
    ) {
        categoriaService.crearCategoria(categoria);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Se registro exitosamente");
    }
    @PostMapping(
            value = "{id}/categoria-image",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public void uploadCategoryImage(
            @PathVariable("id") Integer id,
            @RequestParam("file") MultipartFile file) {
        categoriaService.uploadCategoryImage(id, file);
    }
    @GetMapping(
            value = "{id}/categoria-image",
            produces = MediaType.IMAGE_PNG_VALUE
    )
    public byte[] getCategoryImage(
            @PathVariable("id") Integer id) {
        return categoriaService.getCategoryImagen(id);
    }

    @GetMapping("/listarcategorias-all")
    public ResponseEntity<List<Categorias>> listarCategorias() {
        return ResponseEntity.ok(categoriaService.obtenerListaCategoria());
    }
    @DeleteMapping("{id}/eliminar")
    public void eliminarCategorias(@PathVariable("id") Integer id) {
        categoriaService.eliminarCategoria(id);
    }
    @PutMapping("{id}/actualizar")
    public void actualizarCategorias(@PathVariable("id") Integer id,
                                     @RequestBody CategoriaUpdateRequest updateRequest){
        categoriaService.actualizarCategorias(id, updateRequest);
    }
    @GetMapping("{id}")
    public Categorias obtenerCategoria(@PathVariable("id") Integer id){
        return categoriaService.getCategorias(id);
    }

}
