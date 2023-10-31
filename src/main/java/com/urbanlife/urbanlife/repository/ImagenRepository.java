package com.urbanlife.urbanlife.repository;


import com.urbanlife.urbanlife.models.ImagenesProducto;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Transactional
@Repository
public interface ImagenRepository extends JpaRepository<ImagenesProducto, Integer> {

    @Modifying
    @Query (value = "Select * from imagenes_producto as ima\n" +
            "where ima.id_producto = :id", nativeQuery = true)
    List<ImagenesProducto> findByProductosIdProducto(@Param("id") Integer id);
}
