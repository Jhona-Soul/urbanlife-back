package com.urbanlife.urbanlife.repository;

import com.urbanlife.urbanlife.models.Reservas;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
@Transactional
@Repository
public interface ReservaRepository extends JpaRepository<Reservas, Integer> {


}
