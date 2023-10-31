package com.urbanlife.urbanlife.repository;

import com.urbanlife.urbanlife.models.Caracteristicas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CaracteristicasRepository extends JpaRepository<Caracteristicas, Integer> {
}
