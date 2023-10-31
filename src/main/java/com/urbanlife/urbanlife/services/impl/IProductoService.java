package com.urbanlife.urbanlife.services.impl;
import com.urbanlife.urbanlife.models.request.BusquedaRequest;
import com.urbanlife.urbanlife.models.response.ProductoResponse;
import com.urbanlife.urbanlife.models.Dto.ProductosAletoriosDTO;
import com.urbanlife.urbanlife.models.ProductosDto;
import com.urbanlife.urbanlife.models.request.ProductoRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

public interface IProductoService {
    void registrarProducto(ProductoRequest request);
    void uploadProductImagen(Integer idProducto, MultipartFile file);
    Collection<ProductoResponse> listaProductosAll();
    Collection<ProductosAletoriosDTO> listarProductosAletoriosDTO();
    void eliminarProducto(Integer id);
    void editarProducto(Integer id, ProductoRequest updateRequest);
    ProductoResponse obtenerProducto(Integer id);
    public Collection<ProductosAletoriosDTO> listaProductosBusqueda(BusquedaRequest request);
}
