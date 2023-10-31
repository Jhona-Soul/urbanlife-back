package com.urbanlife.urbanlife.services.impl;

import com.urbanlife.urbanlife.models.Dto.ImagenDto;
import com.urbanlife.urbanlife.models.ImagenesProducto;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface IImagenService {

    void createImagen(ImagenesProducto imagenes);
    List<ImagenesProducto> getAllImagenes();
    byte[] getProductoImagen(Integer id);
    Collection<ImagenDto> listarImagenesPorProducto(Integer id);
    Optional<ImagenesProducto> obtenerImagen(Integer id);
}
