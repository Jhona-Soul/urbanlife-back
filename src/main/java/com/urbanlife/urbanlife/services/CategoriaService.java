package com.urbanlife.urbanlife.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanlife.urbanlife.exception.RequestValidationException;
import com.urbanlife.urbanlife.exception.ResourceNotFoundException;
import com.urbanlife.urbanlife.models.Categorias;
import com.urbanlife.urbanlife.models.Dto.CategoriaDto;
import com.urbanlife.urbanlife.models.response.ProductoResponse;
import com.urbanlife.urbanlife.models.update.CategoriaUpdateRequest;
import com.urbanlife.urbanlife.repository.CategoriaRepository;
import com.urbanlife.urbanlife.s3.S3Buckets;
import com.urbanlife.urbanlife.s3.S3Service;
import com.urbanlife.urbanlife.services.impl.ICategoriaService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoriaService implements ICategoriaService {
    @Autowired
    CategoriaRepository categoriaRepository;
    @Autowired
    ProductoService productoService;
    @Autowired
    ObjectMapper mapper;
    @Autowired
    S3Service s3Service;
    @Autowired
    S3Buckets s3Buckets;
    @Value("${url.imagen}")
    private String ipUrlImagen;
    private static final Logger logger = Logger.getLogger(CategoriaService.class);

    public boolean existsCustomerById(Integer id) {
        Optional<Categorias> categorias = categoriaRepository.findById(id);
        return categorias.isPresent();
    }
    private void checkIfCategoriaExistsOrThrow(Integer idCategoria) {
        if (!existsCustomerById(idCategoria)) {
            throw new ResourceNotFoundException(
                    "La categoria con el id [%s] NO EXISTE".formatted(idCategoria)
            );
        }
    }
    private void guardarCategoria(CategoriaDto categoriaDto) {
        Categorias newCategorias = mapper.convertValue(categoriaDto, Categorias.class);
        categoriaRepository.save(newCategorias);
    }
    @Override
    public void crearCategoria(CategoriaDto categoriaDto) {
        if (categoriaDto != null) {
            guardarCategoria(categoriaDto);
            logger.info("Se registro exitosamente la categoria");
        }else {logger.error("Surgio un problema, no se registro la categoria");}
    }
    @Override
    public void uploadCategoryImage(Integer id, MultipartFile file) {
        checkIfCategoriaExistsOrThrow(id);

        String profileImageId = UUID.randomUUID().toString();
        String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        try {
             s3Service.uploadFile(
                    s3Buckets.getCustomer(),
                    file.getBytes(),
                    "categoria-images/%s/%s.%s".formatted(id, profileImageId,extension)
            );
        } catch (IOException e) {
            throw new RuntimeException("failed to upload profile image category", e);
        }
        categoriaRepository.updateUrlImagen(id,"%s.%s".formatted(profileImageId,extension));
    }
    @Override
    public byte[] getCategoryImagen(Integer idCategoria) {
        var categoria = categoriaRepository.findById(idCategoria)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "La categoria con el id [%s] NO EXISTE".formatted(idCategoria)
                ));

        if (!StringUtils.hasText(categoria.getURLIMAGEN())) {
            throw new ResourceNotFoundException(
                    "Esta categoria [%s] no tiene una imagen asignada".formatted(idCategoria));
        }
        return s3Service.getObjectBytes(
                s3Buckets.getCustomer(),
                "categoria-images/%s/%s".formatted(idCategoria, categoria.getURLIMAGEN())
        );
    }
    @Override
    public void eliminarCategoria(Integer idCategoria) {
        checkIfCategoriaExistsOrThrow(idCategoria);
        categoriaRepository.setEstadoEliminar(idCategoria, true);
        Collection<ProductoResponse> productos = productoService.listaProductosAll();
        productos.stream()
                .peek(productosDto -> productoService.eliminarProducto(productosDto.getIdProducto()))
                .collect(Collectors.toList());
    }
    @Override
    public List<Categorias> obtenerListaCategoria() {
        return categoriaRepository.findAll().stream()
                .peek(categorias -> categorias.setURLIMAGEN("http://%s/categorias/%s/categoria-image"
                        .formatted(ipUrlImagen,categorias.getIdCategoria())))
                .collect(Collectors.toList());
    }
    @Override
    public void actualizarCategorias(Integer id,
                                           CategoriaUpdateRequest updateRequest) {
        checkIfCategoriaExistsOrThrow(id);
        Optional<Categorias> categoria = categoriaRepository.findById(id);
        categoria
                .orElseThrow(() -> new ResourceNotFoundException(
                        "La categoria con el id [%s] NO EXISTE".formatted(id)));

        boolean changes = false;

        if (updateRequest.getDescripcion() != null &&
                !updateRequest.getDescripcion().equals(categoria.get().getDescripcion()))
        {
            categoria.get().setDescripcion(updateRequest.getDescripcion());
            changes = true;
        }
        if (updateRequest.getTitulo() != null &&
                !updateRequest.getTitulo().equals(categoria.get().getTitulo()))
        {
            categoria.get().setTitulo(updateRequest.getTitulo());
            changes = true;
        }
        if (!changes) {
            throw new RequestValidationException("No se encontro datos para actualizar");
        }
        update(categoria);
    }
    void update(Optional<Categorias> categorias) {
        categoriaRepository.setTitulo(
                categorias.get().getTitulo(),
                categorias.get().getIdCategoria());
        categoriaRepository.setDescripcion(
                categorias.get().getDescripcion(),
                categorias.get().getIdCategoria()
        );
    }
    @Override
    public Categorias getCategorias(Integer id) {
        checkIfCategoriaExistsOrThrow(id);
        Optional<Categorias> found = categoriaRepository.findById(id);
        Categorias categorias = mapper.convertValue(found.get(), Categorias.class);
        categorias.setURLIMAGEN("http://%s/categorias/%s/categoria-image".formatted(ipUrlImagen,id));
        return categorias;
    }

}
