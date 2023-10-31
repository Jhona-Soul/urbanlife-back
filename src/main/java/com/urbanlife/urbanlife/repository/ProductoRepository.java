package com.urbanlife.urbanlife.repository;

import com.urbanlife.urbanlife.models.Productos;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;
@Repository
@Transactional
public interface ProductoRepository extends JpaRepository<Productos, Integer> {

    @Modifying
    @Query(value = "SELECT * FROM urbanlife.productos WHERE eliminar_producto <> 1 ORDER BY RAND() LIMIT 10", nativeQuery = true)
    Collection<Productos> listProductosAletorios();

    @Modifying
    @Query(value = "UPDATE productos SET eliminar_producto = :estado WHERE id_producto = :id", nativeQuery = true)
    void setEstadoEliminar(@Param("id") Integer id, @Param("estado") boolean estado);

    @Modifying
    @Query(value = "Insert into producto_medida (cantidad, id_medida, id_producto)\n" +
            "values (:cantidad, :id_medida, :id_producto)", nativeQuery = true)
    void registrarTalleConProducto(@Param("cantidad") Integer cantidad,
                                  @Param("id_medida") Integer id_medida,
                                  @Param("id_producto") Integer id_producto);

    @Modifying
    @Query(value = "INSERT INTO imagenes_producto (url_imagen, id_producto)\n" +
                    "VALUES (:URL_IMAGEN,:id_producto)", nativeQuery = true)
    void resgistrarImagenesConProducto(@Param("URL_IMAGEN") String URL_IMAGEN,
                                       @Param("id_producto") Integer id_producto);

    @Modifying
    @Query(value = """
            UPDATE productos
            SET nombre = :nombre
            WHERE id_producto = :id
            """, nativeQuery = true)
    void cambiarNombre(@Param("id") Integer id, @Param("nombre") String nombre);


    @Modifying
    @Query(value = """
            UPDATE productos
            SET precio = :precio
            WHERE id_producto = :id;
            """, nativeQuery = true)
    void setPrecio(@Param("id") Integer id, @Param("precio") Double precio);

    @Modifying
    @Query(value = """
            UPDATE productos
            SET color = :color
            WHERE id_producto = :id;
            """, nativeQuery = true)
    void setColor(@Param("id") Integer id, @Param("color") String color);
    @Modifying
    @Query(value = """
            UPDATE productos
            SET detalle = :detalle
            WHERE id_producto = :id;
            """, nativeQuery = true)
    void setDetalle(@Param("id") Integer id, @Param("detalle") String detalle);

    @Modifying
    @Query(value = """
            UPDATE productos
            SET id_categoria = :categoria
            WHERE id_producto = :id;
            """,nativeQuery = true)
    void setCategoria(@Param("id") Integer id, @Param("categoria") Integer categoria);
    //---------------------------Search Products--------------------------------
    @Modifying
    @Query(value = """
            select p.* from productos as p
            left join reservas as d on p.id_producto=d.id_producto
            where( d.fecha_inicio_alquiler not between :fechaInicio and :fechaFin
            and d.fecha_fin_alquiler not between :fechaInicio and :fechaFin)\s
            or d.fecha_inicio_alquiler is null and p.eliminar_producto = 0""", nativeQuery = true)
    Collection<Productos> listaProductosBaseFechaReserva(@Param("fechaInicio")LocalDate fechaInicio,
                                                         @Param("fechaFin")LocalDate fechaFin);
    @Modifying
    @Query(value = """
            select p.* from productos as p
            left join reservas as d on p.id_producto=d.id_producto
            where nombre like %:nombre% and\s
            nombre like %:nombreDos%\s
            and nombre like %:nombreTres%
            and p.eliminar_producto = 0;
            """, nativeQuery = true)
    Collection<Productos> listaProductosBaseNombreReserva(
            @Param("nombre") String nombre,
            @Param("nombreDos") String nombreDos,
            @Param("nombreTres") String nombreTres
    );
    @Modifying
    @Query(value = """
                select p.* from productos as p
                left join reservas as d on p.id_producto=d.id_producto
                where( d.fecha_inicio_alquiler not between :fechaInicio and :fechaFin
                and d.fecha_fin_alquiler not between :fechaInicio and :fechaFin)\s
                or d.fecha_inicio_alquiler is null and nombre like %:nombre%
                and p.eliminar_producto = 0;
            """, nativeQuery = true)
    Collection<Productos> listaProductosBaseReserva(
        @Param("nombre") String nombre,
        @Param("fechaInicio")LocalDate fechaInicio,
        @Param("fechaFin")LocalDate fechaFin
    );

    @Modifying
    @Query(value = """
            select p.* from productos as p\s
            inner join favoritos as f
            where p.id_producto = f.id_producto
            and f.id_usuario = :idUsuario""", nativeQuery = true)
    Collection<Productos> listaFavoritos(@Param("idUsuario")Integer idUsuario);

}
