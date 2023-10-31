package com.urbanlife.urbanlife.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanlife.urbanlife.exception.DuplicateResourceException;
import com.urbanlife.urbanlife.exception.RequestValidationException;
import com.urbanlife.urbanlife.exception.ResourceNotFoundException;
import com.urbanlife.urbanlife.models.response.ProductoResponse;
import com.urbanlife.urbanlife.models.Dto.ProductosAletoriosDTO;
import com.urbanlife.urbanlife.models.Medidas;
import com.urbanlife.urbanlife.models.Productos;
import com.urbanlife.urbanlife.models.request.BusquedaRequest;
import com.urbanlife.urbanlife.models.request.ProductoRequest;
import com.urbanlife.urbanlife.repository.ProductoRepository;
import com.urbanlife.urbanlife.s3.S3Buckets;
import com.urbanlife.urbanlife.s3.S3Service;
import com.urbanlife.urbanlife.services.impl.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductoService implements IProductoService {
    @Autowired
    ProductoRepository productoRepository;
    @Autowired
    MedidaService medidaService;
    @Autowired
    ImagenService imagenService;
    @Autowired
    S3Service s3Service;
    @Autowired
    S3Buckets s3Buckets;
    @Autowired
    ObjectMapper objectMapper;
    private static final Logger logger = Logger.getLogger(ProductoService.class);
    public boolean existsCustomerById(Integer id) {
        Optional<Productos> producto = productoRepository.findById(id);
        return producto.isPresent();
    }
    private void checkIfProductoExistsOrThrow(Integer id) {
        if (!existsCustomerById(id)) {
            throw new ResourceNotFoundException(
                    "El producto con el id [%s] NO EXISTE".formatted(id)
            );
        }
    }
    public void registrarProducto(ProductoRequest request) {
        Collection<ProductoResponse> listaProductos = listaProductosAll();
        if (request != null ) {
            for (ProductoResponse producto : listaProductos) {
                if (request.getNombre().equals(producto.getNombre())) {
                    logger.error("Este Producto ya existe!");
                    throw new DuplicateResourceException(
                            "Este Producto ya existe!"
                    );
                }
            }
            guardarProductoCompleto(request);
        }else { logger.error("Surgio un problema");}
    }
    private void guardarProductoCompleto (ProductoRequest request) {
        var producto = Productos.builder()
                .nombre(request.getNombre())
                .precio(request.getPrecio())
                .detalle(request.getDetalle())
                .color(request.getColor())
                .fechaActual(LocalDate.now())
                .eliminarProducto(false)
                .puntuacion(0.0)
                .categorias(request.getCategorias())
                .build();
        productoRepository.save(producto);
        checkIfProductoExistsOrThrow(producto.getIdProducto());
        Collection<Medidas> listaMedidasRequest = request.getTalles();
        for (Medidas talle : listaMedidasRequest) {
            productoRepository.registrarTalleConProducto(
                    10,
                    talle.getIdMedida(),
                    producto.getIdProducto()
            );
        }
    }
    @Override
    public void uploadProductImagen(Integer idProduct,
                                           MultipartFile file) {
        checkIfProductoExistsOrThrow(idProduct);
        String profileImageId = UUID.randomUUID().toString();
        String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        try {
            s3Service.uploadFile(
                    s3Buckets.getCustomer(),
                    file.getBytes(),
                    "producto-images/%s/%s.%s".formatted(idProduct, profileImageId,extension)
            );
        } catch (IOException e) {
            throw new RuntimeException("failed to upload profile image", e);
        }
        productoRepository.resgistrarImagenesConProducto(
                "%s.%s".formatted(profileImageId, extension),
                idProduct);
    }
    @Override
    public Collection<ProductoResponse> listaProductosAll() {
        Iterable<Productos> listarProductos = productoRepository.findAll();
        Set<ProductoResponse> listarProductoResponse = new HashSet<ProductoResponse>();
        for (Productos producto : listarProductos) {
            if (!producto.getEliminarProducto()) {
                listarProductoResponse.add(objectMapper.convertValue(producto, ProductoResponse.class));
            }
        }
        logger.info("Lista Productos: Proceso Finalizado con Exito!");
        return listarProductoResponse
                .stream()
                .peek(productoResponse -> {
                    productoResponse.setTalles(medidaService.listarTallesProducto(productoResponse.getIdProducto()));
                    productoResponse.setImagenes(imagenService.listarImagenesPorProducto(productoResponse.getIdProducto()));
                })
                .collect(Collectors.toList());
    }
    @Override
    public Collection<ProductosAletoriosDTO> listarProductosAletoriosDTO() {
        Iterable<Productos> listaProductos = productoRepository.listProductosAletorios();
        Set<ProductosAletoriosDTO> listaProductosDTO = new HashSet<ProductosAletoriosDTO>();
        for (Productos productos : listaProductos) {
            listaProductosDTO.add(objectMapper.convertValue(productos, ProductosAletoriosDTO.class));
        }
        logger.info("Productos Aleatorios: Proceso Finalizado con Exito!");
        return listaProductosDTO
                .stream()
                .peek(productoDTO -> productoDTO.setImagenes(imagenService.listarImagenesPorProducto(productoDTO.getIdProducto())))
                .collect(Collectors.toList());
    }
    @Override
    public ProductoResponse obtenerProducto(Integer id) {
        checkIfProductoExistsOrThrow(id);
        Optional<Productos> producto = productoRepository.findById(id);
        ProductoResponse result = objectMapper.convertValue(producto, ProductoResponse.class);
        result.setTalles(medidaService.listarTallesProducto(id));
        result.setImagenes(imagenService.listarImagenesPorProducto(id));
        return result;
    }

    @Override
    public void editarProducto(Integer id, ProductoRequest updateRequest) {
        Optional<Productos> producto = productoRepository.findById(id);
        producto
                .orElseThrow(() -> new ResourceNotFoundException(
                "El producton con el id [%s] NO EXISTE".formatted(id)));

        boolean changes = false;
        if (updateRequest.getNombre() != null &&
                !updateRequest.getNombre().equals(producto.get().getNombre())) {
            //producto.get().setNombre(updateRequest.getNombre());
            productoRepository.cambiarNombre(
                    producto.get().getIdProducto(),
                    updateRequest.getNombre()
            );
            logger.info("Producto nombre: Se actualizó exitosamente!");
            changes = true;
        }

        if (updateRequest.getPrecio() != null && !updateRequest.getPrecio().equals(producto.get().getPrecio())) {
            //producto.get().setPrecio(updateRequest.getPrecio());
            productoRepository.setPrecio(
                    producto.get().getIdProducto(),
                    updateRequest.getPrecio()
            );
            logger.info("Producto - precio: Se actualizó exitosamente!");
            changes = true;
        }

        if (updateRequest.getDetalle() != null &&
                !updateRequest.getDetalle().equals(producto.get().getDetalle())) {
            //producto.get().setDetalle(updateRequest.getDetalle());
            productoRepository.setDetalle(
                    producto.get().getIdProducto(),
                    updateRequest.getDetalle()
            );
            logger.info("Producto - Detalle: Se actualizó exitosamente!");
            changes = true;
        }

        if (updateRequest.getColor() != null &&
                !updateRequest.getColor().equals(producto.get().getColor())) {
            //producto.get().setColor(updateRequest.getColor());
            productoRepository.setColor(
                    producto.get().getIdProducto(),
                    updateRequest.getColor()
            );
            logger.info("Producto- Color: Se actualizó exitosamente!");
            changes = true;
        }

        if (
                updateRequest.getCategorias() != null &&
                updateRequest.getCategorias().getIdCategoria() !=
                        producto.get().getCategorias().getIdCategoria())
        {
           //producto.get().setCategorias(updateRequest.getCategorias());
           productoRepository.setCategoria(
                   producto.get().getIdProducto(),
                   producto.get().getCategorias().getIdCategoria()
           );
            logger.info("Producto - Categoria: Se actualizó exitosamente!");
           changes = true;
        }

        if (!changes) {
            throw new RequestValidationException("no data changes found");
        }
    }
    @Override
    public void eliminarProducto(Integer id) {
        productoRepository.setEstadoEliminar(id, true);
    }
    private Collection<Productos> busquedaDelProducto(BusquedaRequest request) {
        if (request.getNombre() == null) {
            return productoRepository.listaProductosBaseFechaReserva(
                    request.getFechaInicio(), request.getFechaFin()
            );
        }
        if(request.getFechaInicio() == null && request.getFechaFin() == null) {
            String[] partes = request.getNombre().split(" ");
            switch (partes.length) {
                case 1 -> {
                    return productoRepository.listaProductosBaseNombreReserva(partes[0], "", "");
                }
                case 2 -> {
                    return productoRepository.listaProductosBaseNombreReserva(partes[0], partes[1], "");
                }
                case 3 -> {
                    return productoRepository.listaProductosBaseNombreReserva(
                            partes[0],
                            partes[1],
                            partes[2]);
                }
                default -> {
                    logger.warn("Search Productos: No se encontro ningun producto!");
                    return null;
                }
            }
        }

        return productoRepository.listaProductosBaseReserva(
                request.getNombre(),
                request.getFechaInicio(),
                request.getFechaFin()
        );
    }
    @Override
    public Collection<ProductosAletoriosDTO> listaProductosBusqueda(BusquedaRequest request) {
        Iterable<Productos> listaProductos = busquedaDelProducto(request);

        Set<ProductosAletoriosDTO> listaProductosDTO = new HashSet<ProductosAletoriosDTO>();
        for (Productos productos : listaProductos) {
            listaProductosDTO.add(objectMapper.convertValue(productos, ProductosAletoriosDTO.class));
        }
        logger.info("SearchProductos: Proceso Finalizado con Exito!");
        return listaProductosDTO
                .stream()
                .peek(productoDTO -> productoDTO.setImagenes(imagenService.listarImagenesPorProducto(productoDTO.getIdProducto())))
                .collect(Collectors.toList());
    }
}
