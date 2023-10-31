package com.urbanlife.urbanlife.repository;

import com.urbanlife.urbanlife.models.Dto.MedidaDto;
import com.urbanlife.urbanlife.models.Medidas;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedidaRepository extends JpaRepository<Medidas, Integer> {
    @Transactional
    @Modifying
    @Query (value = "SELECT pm.id_producto, pm.id_medida, m.talle\n" +
            "FROM producto_medida AS pm\n" +
            "LEFT JOIN medidas AS m ON pm.id_medida = m.id_medida where  pm.id_producto = :id", nativeQuery = true)
    List<Medidas> listarTallesEnBaseProducto(@Param("id")Integer id);
}
