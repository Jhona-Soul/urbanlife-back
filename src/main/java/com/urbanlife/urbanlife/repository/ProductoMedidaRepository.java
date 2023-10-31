package com.urbanlife.urbanlife.repository;

import com.urbanlife.urbanlife.models.ProductoMedida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoMedidaRepository extends JpaRepository<ProductoMedida, Integer> {

}
