package com.LusoSAC.Sistema_Ecommerce.repository;

import com.LusoSAC.Sistema_Ecommerce.dto.producto.ProductoCardResponse;
import com.LusoSAC.Sistema_Ecommerce.model.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    @Query(value = """
        SELECT p.*
        FROM productos p
        LEFT JOIN producto_metricas pm ON pm.id_producto = p.id_producto
        WHERE p.estado_producto = 'ACTIVO'
        ORDER BY
            COALESCE(p.es_destacado, 0) DESC,
            COALESCE(pm.score_producto, 0) DESC,
            p.id_producto DESC
        LIMIT 10
    """, nativeQuery = true)
    List<Producto> obtenerProductosDestacados();

    @Query(value = """
        SELECT p.*
        FROM productos p
        LEFT JOIN producto_metricas pm ON pm.id_producto = p.id_producto
        WHERE p.estado_producto = 'ACTIVO'
        ORDER BY
            COALESCE(pm.total_vistas, 0)
            + COALESCE(pm.total_clicks, 0) * 3
            + COALESCE(pm.total_whatsapp, 0) * 6
            + COALESCE(pm.total_consultas, 0) * 8
            + COALESCE(pm.total_ventas, 0) * 15 DESC,
            p.id_producto DESC
        LIMIT 10
    """, nativeQuery = true)
    List<Producto> obtenerProductosRecomendados();

    @Query(value = """
        SELECT p.*
        FROM productos p
        LEFT JOIN producto_metricas pm ON pm.id_producto = p.id_producto
        WHERE p.estado_producto = 'ACTIVO'
        ORDER BY
            COALESCE(pm.total_vistas, 0) DESC,
            p.id_producto DESC
        LIMIT 10
    """, nativeQuery = true)
    List<Producto> obtenerProductosPopulares();

    @Query(value = """
        SELECT p.*
        FROM productos p
        LEFT JOIN producto_metricas pm ON pm.id_producto = p.id_producto
        WHERE p.estado_producto = 'ACTIVO'
        ORDER BY
            COALESCE(pm.score_producto, 0) DESC,
            COALESCE(pm.total_vistas, 0) DESC,
            p.id_producto DESC
        LIMIT 10
    """, nativeQuery = true)
    List<Producto> recomendarGeneral();

    @Query(value = """
        SELECT p.*
        FROM productos p
        LEFT JOIN producto_metricas pm ON pm.id_producto = p.id_producto
        WHERE p.id_categoria IN (
            SELECT DISTINCT p2.id_categoria
            FROM productos p2
            INNER JOIN interacciones i ON i.id_producto = p2.id_producto
            WHERE i.id_visitante = :idVisitante
        )
        AND p.estado_producto = 'ACTIVO'
        AND p.id_producto NOT IN (
            SELECT i2.id_producto
            FROM interacciones i2
            WHERE i2.id_visitante = :idVisitante
              AND i2.id_producto IS NOT NULL
        )
        ORDER BY
            COALESCE(pm.score_producto, 0) DESC,
            COALESCE(pm.total_vistas, 0) DESC
        LIMIT 10
    """, nativeQuery = true)
    List<Producto> recomendarPorVisitante(@Param("idVisitante") Long idVisitante);

    @Query(value = """
        SELECT *
        FROM productos
        WHERE estado_producto = 'ACTIVO'
        ORDER BY RAND()
        LIMIT 10
    """, nativeQuery = true)
    List<Producto> obtenerAleatorios();

    @Query("""
        SELECT p
        FROM Producto p
        WHERE p.estado = 'ACTIVO'
          AND (
                LOWER(p.nombre) LIKE LOWER(CONCAT('%', :q, '%'))
             OR LOWER(p.modelo) LIKE LOWER(CONCAT('%', :q, '%'))
             OR LOWER(p.descripcion) LIKE LOWER(CONCAT('%', :q, '%'))
          )
    """)
    List<Producto> buscar(@Param("q") String q);

    @Query("""
        SELECT new com.LusoSAC.Sistema_Ecommerce.dto.producto.ProductoCardResponse(
            p.id,
            p.nombre,
            p.modelo,
            p.precio,
            p.stock,
            p.estado,
            p.idCategoria,
            p.idMarca,
            CASE WHEN p.categoria IS NOT NULL THEN p.categoria.nombre ELSE null END,
            CASE WHEN p.marca IS NOT NULL THEN p.marca.nombre ELSE null END
        )
        FROM Producto p
        WHERE p.estado = 'ACTIVO'
        ORDER BY p.id DESC
    """)
    List<ProductoCardResponse> listarCards();

    @Query("""
        SELECT p
        FROM Producto p
        WHERE
            (:q IS NULL OR :q = '' OR
                LOWER(p.nombre) LIKE LOWER(CONCAT('%', :q, '%')) OR
                LOWER(p.modelo) LIKE LOWER(CONCAT('%', :q, '%')) OR
                LOWER(p.descripcion) LIKE LOWER(CONCAT('%', :q, '%'))
            )
        AND (:idCategoria IS NULL OR p.idCategoria = :idCategoria)
        AND (:idMarca IS NULL OR p.idMarca = :idMarca)
        AND (:modelo IS NULL OR :modelo = '' OR LOWER(p.modelo) = LOWER(:modelo))
        AND (:precioMin IS NULL OR p.precio >= :precioMin)
        AND (:precioMax IS NULL OR p.precio <= :precioMax)
        AND p.estado = 'ACTIVO'
    """)
    Page<Producto> filtrarProductos(
            @Param("q") String q,
            @Param("idCategoria") Long idCategoria,
            @Param("idMarca") Long idMarca,
            @Param("modelo") String modelo,
            @Param("precioMin") BigDecimal precioMin,
            @Param("precioMax") BigDecimal precioMax,
            Pageable pageable
    );

    @Query(value = """
        SELECT DISTINCT p.*
        FROM productos p
        INNER JOIN interacciones i ON i.id_producto = p.id_producto
        WHERE i.id_visitante = :idVisitante
          AND p.estado_producto = 'ACTIVO'
        ORDER BY i.fecha_creacion DESC
        LIMIT 10
    """, nativeQuery = true)
    List<Producto> productosVistosPorVisitante(@Param("idVisitante") Long idVisitante);

    @Query("""
        SELECT new com.LusoSAC.Sistema_Ecommerce.dto.producto.ProductoCardResponse(
            p.id,
            p.nombre,
            p.modelo,
            p.precio,
            p.stock,
            p.estado,
            p.idCategoria,
            p.idMarca,
            CASE WHEN p.categoria IS NOT NULL THEN p.categoria.nombre ELSE null END,
            CASE WHEN p.marca IS NOT NULL THEN p.marca.nombre ELSE null END
        )
        FROM Producto p
        WHERE p.id IN :ids
    """)
    List<ProductoCardResponse> cardsPorIds(@Param("ids") List<Long> ids);
    @Query("""
    SELECT new com.LusoSAC.Sistema_Ecommerce.dto.producto.ProductoCardResponse(
        p.id,
        p.nombre,
        p.modelo,
        p.precio,
        p.stock,
        p.estado,
        p.idCategoria,
        p.idMarca,
        CASE WHEN p.categoria IS NOT NULL THEN p.categoria.nombre ELSE null END,
        CASE WHEN p.marca IS NOT NULL THEN p.marca.nombre ELSE null END
    )
    FROM Producto p
    WHERE p.estado = 'ACTIVO'
      AND (
            LOWER(p.nombre) LIKE LOWER(CONCAT('%', :q, '%'))
         OR LOWER(p.modelo) LIKE LOWER(CONCAT('%', :q, '%'))
         OR LOWER(p.descripcion) LIKE LOWER(CONCAT('%', :q, '%'))
      )
    ORDER BY p.id DESC
""")
    List<ProductoCardResponse> buscarCards(@Param("q") String q);


    @Query("""
    SELECT new com.LusoSAC.Sistema_Ecommerce.dto.producto.ProductoCardResponse(
        p.id,
        p.nombre,
        p.modelo,
        p.precio,
        p.stock,
        p.estado,
        p.idCategoria,
        p.idMarca,
        CASE WHEN p.categoria IS NOT NULL THEN p.categoria.nombre ELSE null END,
        CASE WHEN p.marca IS NOT NULL THEN p.marca.nombre ELSE null END
    )
    FROM Producto p
    WHERE p.estado = 'ACTIVO'
""")
    Page<ProductoCardResponse> listarCardsPaginado(Pageable pageable);


    @Query("""
    SELECT new com.LusoSAC.Sistema_Ecommerce.dto.producto.ProductoCardResponse(
        p.id,
        p.nombre,
        p.modelo,
        p.precio,
        p.stock,
        p.estado,
        p.idCategoria,
        p.idMarca,
        CASE WHEN p.categoria IS NOT NULL THEN p.categoria.nombre ELSE null END,
        CASE WHEN p.marca IS NOT NULL THEN p.marca.nombre ELSE null END
    )
    FROM Producto p
    WHERE
        (:q IS NULL OR :q = '' OR
            LOWER(p.nombre) LIKE LOWER(CONCAT('%', :q, '%')) OR
            LOWER(p.modelo) LIKE LOWER(CONCAT('%', :q, '%')) OR
            LOWER(p.descripcion) LIKE LOWER(CONCAT('%', :q, '%'))
        )
    AND (:idCategoria IS NULL OR p.idCategoria = :idCategoria)
    AND (:idMarca IS NULL OR p.idMarca = :idMarca)
    AND (:modelo IS NULL OR :modelo = '' OR LOWER(p.modelo) = LOWER(:modelo))
    AND (:precioMin IS NULL OR p.precio >= :precioMin)
    AND (:precioMax IS NULL OR p.precio <= :precioMax)
    AND p.estado = 'ACTIVO'
""")
    Page<ProductoCardResponse> filtrarProductosCards(
            @Param("q") String q,
            @Param("idCategoria") Long idCategoria,
            @Param("idMarca") Long idMarca,
            @Param("modelo") String modelo,
            @Param("precioMin") BigDecimal precioMin,
            @Param("precioMax") BigDecimal precioMax,
            Pageable pageable
    );


    default List<ProductoCardResponse> obtenerProductosDestacadosCards() {
        return cardsPorIds(obtenerProductosDestacados()
                .stream()
                .map(Producto::getId)
                .toList());
    }

    default List<ProductoCardResponse> obtenerProductosRecomendadosCards() {
        return cardsPorIds(obtenerProductosRecomendados()
                .stream()
                .map(Producto::getId)
                .toList());
    }

    default List<ProductoCardResponse> obtenerProductosPopularesCards() {
        return cardsPorIds(obtenerProductosPopulares()
                .stream()
                .map(Producto::getId)
                .toList());
    }

    default List<ProductoCardResponse> recomendarGeneralCards() {
        return cardsPorIds(recomendarGeneral()
                .stream()
                .map(Producto::getId)
                .toList());
    }

    default List<ProductoCardResponse> recomendarPorVisitanteCards(Long idVisitante) {
        return cardsPorIds(recomendarPorVisitante(idVisitante)
                .stream()
                .map(Producto::getId)
                .toList());
    }

}
