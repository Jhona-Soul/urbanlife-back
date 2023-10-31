package com.urbanlife.urbanlife.repository;

import com.urbanlife.urbanlife.models.Categorias;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface CategoriaRepository extends JpaRepository<Categorias, Integer> {
    @Modifying
    @Query(value = """
            UPDATE categorias
            SET urlimagen = :urlImagen
            WHERE id_categoria = :id""",nativeQuery = true)
    void updateUrlImagen(@Param("id")Integer id, @Param("urlImagen")String urlImagen);
    @Modifying
    @Query(value = "UPDATE categorias SET eliminar_categoria = :estado WHERE id_categoria = :id", nativeQuery = true)
    void setEstadoEliminar(@Param("id") Integer id, @Param("estado") boolean estado);
    @Modifying
    @Query(value ="""
            UPDATE categorias
            SET titulo = :titulo
            WHERE id_categoria = :id
            """, nativeQuery = true)
    void setTitulo(@Param("titulo") String titulo,
                        @Param("id") Integer id);
    @Modifying
    @Query(value ="""
            UPDATE categorias
            SET descripcion = :descripcion
            WHERE id_categoria = :id
            """, nativeQuery = true)
    void setDescripcion(@Param("descripcion") String descripcion,
                        @Param("id") Integer id);

    //----Investigar Error -----
    @Modifying
    @Query(value ="SELECT * FROM categorias where id_categoria = :id", nativeQuery = true)
    Categorias obtenerCategoria(@Param("id") Integer id);
}
