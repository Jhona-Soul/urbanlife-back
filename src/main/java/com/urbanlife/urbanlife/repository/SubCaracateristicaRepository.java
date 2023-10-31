package com.urbanlife.urbanlife.repository;

import com.urbanlife.urbanlife.models.SubCaracteristicas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubCaracateristicaRepository extends JpaRepository<SubCaracteristicas, Integer> {
}
