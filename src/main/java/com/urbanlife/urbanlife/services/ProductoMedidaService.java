package com.urbanlife.urbanlife.services;

import com.urbanlife.urbanlife.models.ProductoMedida;
import com.urbanlife.urbanlife.repository.ProductoMedidaRepository;
import com.urbanlife.urbanlife.services.impl.IProductoMediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ProductoMedidaService implements IProductoMediaService {
    @Autowired
    ProductoMedidaRepository productoMedidaRepository;
    @Override
    public ProductoMedida crearProducto(ProductoMedida productoMedida) {
        return productoMedidaRepository.save(productoMedida);
    }

    @Override
    public Collection<ProductoMedida> obtenerListaProductoMedida() {
        return productoMedidaRepository.findAll();
    }
}
