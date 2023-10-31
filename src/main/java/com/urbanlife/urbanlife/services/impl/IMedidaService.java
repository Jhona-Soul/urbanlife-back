package com.urbanlife.urbanlife.services.impl;

import com.urbanlife.urbanlife.models.Dto.MedidaDto;
import com.urbanlife.urbanlife.models.Medidas;

import java.util.List;

public interface IMedidaService {
    void createMedida(Medidas medidas);
    List<Medidas> getAllMedidas();
    List<Medidas> listarTallesProducto(Integer id);
}
