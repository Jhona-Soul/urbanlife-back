package com.urbanlife.urbanlife.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanlife.urbanlife.exception.ResourceNotFoundException;
import com.urbanlife.urbanlife.models.Dto.ImagenDto;
import com.urbanlife.urbanlife.models.ImagenesProducto;
import com.urbanlife.urbanlife.repository.ImagenRepository;
import com.urbanlife.urbanlife.s3.S3Buckets;
import com.urbanlife.urbanlife.s3.S3Service;
import com.urbanlife.urbanlife.services.impl.IImagenService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ImagenService implements IImagenService {
    @Autowired
    ImagenRepository imagenRepository;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    S3Service s3Service;
    @Autowired
    S3Buckets s3Buckets;
    @Value("${url.imagen}")
    private String ipUrlImagen;
    private static final Logger logger = Logger.getLogger(ImagenService.class);
    @Override
    public void createImagen(ImagenesProducto imagenes) {
        if (imagenes != null) {
            guardarImagen(imagenes);
            logger.info("Se guardo la imagen");
        }else {logger.error("Surgio un problema, no se guardo la imagen");}
    }
    private ImagenesProducto guardarImagen(ImagenesProducto imagenes) {
        return imagenRepository.save(imagenes);
    }
    @Override
    public List<ImagenesProducto> getAllImagenes() {
        return imagenRepository.findAll();
    }
    public Optional<ImagenesProducto> obtenerImagen(Integer id) {
        return imagenRepository.findById(id);
    }
    @Override
    public Collection<ImagenDto> listarImagenesPorProducto(Integer id) {
        Iterable<ImagenesProducto>listaImagenes = imagenRepository.findByProductosIdProducto(id);
        Set<ImagenDto> imagenesPorProducto = new HashSet<ImagenDto>();

        for (ImagenesProducto imagen : listaImagenes) {
            imagenesPorProducto.add(objectMapper.convertValue(imagen, ImagenDto.class));
        }
        logger.info("Proceso Finalizado con Exito!");
        return imagenesPorProducto.stream()
                .peek(imagen -> {imagen.setUrlImagen("http://%s/imagenes/%s/producto-image"
                        .formatted(ipUrlImagen,imagen.getIdImagen()));})
                .collect(Collectors.toList());
    }
    public byte[] getProductoImagen(Integer idImagen) {
        var imagen = imagenRepository.findById(idImagen)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "La categoria con el id [%s] NO EXISTE".formatted(idImagen)
                ));

        if (!StringUtils.hasText(imagen.getUrlImagen())) {
            throw new ResourceNotFoundException(
                    "Esta Imagen [%s] no tiene una URL asignada".formatted(idImagen));
        }
        return s3Service.getObjectBytes(
                s3Buckets.getCustomer(),
                "producto-images/%s/%s".formatted(imagen.getProductos().getIdProducto(), imagen.getUrlImagen())
        );
    }
}
