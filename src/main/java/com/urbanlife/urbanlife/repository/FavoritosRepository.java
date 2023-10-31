package com.urbanlife.urbanlife.repository;

import com.urbanlife.urbanlife.models.Favoritos;
import com.urbanlife.urbanlife.models.Productos;
import com.urbanlife.urbanlife.models.usuario.Usuario;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public interface FavoritosRepository extends JpaRepository<Favoritos, Integer> {
    @Modifying
    @Query(value= """
            Select f.*\s
            From favoritos as f
            where f.id_producto = :idProducto
            and f.id_usuario = :idUsuario""", nativeQuery = true)
    Optional<Favoritos> busquedaFavorito(@Param("idProducto") Integer idProducto,
                                         @Param("idUsuario") Integer idUsuario);
    boolean existsByUsuarioAndProductos(Usuario usuario, Productos productos);
    Favoritos findByUsuarioAndProductos(Usuario usuario, Productos productos);

}
