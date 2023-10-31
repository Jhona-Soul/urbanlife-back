package com.urbanlife.urbanlife.Services;

import com.urbanlife.urbanlife.models.Medidas;
import com.urbanlife.urbanlife.services.MedidaService;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

public class MedidaTest {


    @Test

    @Order(1)

    public void saveMedidaTest() {
        // Crear una instancia de Medidas para ser creado
        Medidas medidas = new Medidas(1,"XL");

        // Crear una instancia de ProductoService
        MedidaService medidaService = new MedidaService();

        // Llamar al método no estático crearProducto en la instancia de ProductoService
        medidaService.saveMedida(medidas);


    }

}
