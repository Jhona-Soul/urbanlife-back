package com.urbanlife.urbanlife.services.impl;

import com.urbanlife.urbanlife.models.ProductoMedida;

import java.util.Collection;

public interface IProductoMediaService {
    ProductoMedida crearProducto(ProductoMedida productoMedida);
    Collection<ProductoMedida> obtenerListaProductoMedida();
}
