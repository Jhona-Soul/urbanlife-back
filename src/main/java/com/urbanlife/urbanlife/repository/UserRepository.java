package com.urbanlife.urbanlife.repository;

import com.urbanlife.urbanlife.models.usuario.Usuario;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByEmail(String email);
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO urbanlife.favoritos (id_usuario, id_producto)\n" +
            "VALUES (:idUsuario, :idProducto)", nativeQuery = true)
    void registrarFavoritoUsuario(@Param("idUsuario") Integer idUsuario,
                                  @Param("idProducto") Integer idProducto);

}