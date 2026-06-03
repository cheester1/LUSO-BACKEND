package com.LusoSAC.Sistema_Ecommerce.repository;

import com.LusoSAC.Sistema_Ecommerce.dto.dashboard.ProductoAbandonoResponse;
import com.LusoSAC.Sistema_Ecommerce.model.ProductoMetrica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductoMetricaRepository extends JpaRepository<ProductoMetrica, Long> {

    List<ProductoMetrica> findTop10ByOrderByScoreProductoDesc();

    List<ProductoMetrica> findTop10ByOrderByTotalVistasDesc();

    List<ProductoMetrica> findTop10ByOrderByTotalClicksDesc();

    List<ProductoMetrica> findTop10ByOrderByTotalWhatsappDesc();

    @Query(value = """
        SELECT
            p.id_producto,
            p.nombre_producto,
            p.modelo_producto,
            COALESCE(m.nombre_marca, ''),
            COALESCE(c.nombre_categoria, ''),
            CASE
                WHEN :tipo = 'clicks' THEN COALESCE(pm.total_clicks, 0)
                WHEN :tipo = 'favoritos' THEN COALESCE(pm.total_favoritos, 0)
                WHEN :tipo = 'whatsapp' THEN COALESCE(pm.total_whatsapp, 0)
                WHEN :tipo = 'vistas' THEN COALESCE(pm.total_vistas, 0)
                ELSE 0
            END AS total,
            CASE
                WHEN totals.total_general = 0 THEN 0
                ELSE ROUND(
                    (
                        CASE
                            WHEN :tipo = 'clicks' THEN COALESCE(pm.total_clicks, 0)
                            WHEN :tipo = 'favoritos' THEN COALESCE(pm.total_favoritos, 0)
                            WHEN :tipo = 'whatsapp' THEN COALESCE(pm.total_whatsapp, 0)
                            WHEN :tipo = 'vistas' THEN COALESCE(pm.total_vistas, 0)
                            ELSE 0
                        END / totals.total_general
                    ) * 100, 2
                )
            END AS porcentaje
        FROM productos p
        LEFT JOIN producto_metricas pm ON pm.id_producto = p.id_producto
        LEFT JOIN marcas m ON m.id_marca = p.id_marca
        LEFT JOIN categorias c ON c.id_categoria = p.id_categoria
        CROSS JOIN (
            SELECT
                CASE
                    WHEN :tipo = 'clicks' THEN COALESCE(SUM(total_clicks), 0)
                    WHEN :tipo = 'favoritos' THEN COALESCE(SUM(total_favoritos), 0)
                    WHEN :tipo = 'whatsapp' THEN COALESCE(SUM(total_whatsapp), 0)
                    WHEN :tipo = 'vistas' THEN COALESCE(SUM(total_vistas), 0)
                    ELSE 0
                END AS total_general
            FROM producto_metricas
        ) totals
        WHERE p.estado_producto = 'ACTIVO'
        ORDER BY total DESC, p.id_producto DESC
        LIMIT 3
    """, nativeQuery = true)
    List<Object[]> top3PorMetrica(@Param("tipo") String tipo);

    @Query(value = """
        SELECT
            p.id_producto,
            p.nombre_producto,
            p.modelo_producto,
            COALESCE(m.nombre_marca, ''),
            COALESCE(c.nombre_categoria, ''),
            COALESCE(pm.total_vistas, 0) AS total,
            CASE
                WHEN COALESCE(pm.total_vistas, 0) = 0 THEN 0
                ELSE ROUND(
                    (
                        (COALESCE(pm.total_vistas, 0)
                         - COALESCE(pm.total_clicks, 0)
                         - COALESCE(pm.total_whatsapp, 0)
                         - COALESCE(pm.total_consultas, 0))
                        / COALESCE(pm.total_vistas, 1)
                    ) * 100, 2
                )
            END AS porcentaje
        FROM productos p
        LEFT JOIN producto_metricas pm ON pm.id_producto = p.id_producto
        LEFT JOIN marcas m ON m.id_marca = p.id_marca
        LEFT JOIN categorias c ON c.id_categoria = p.id_categoria
        WHERE p.estado_producto = 'ACTIVO'
          AND COALESCE(pm.total_vistas, 0) > 0
        ORDER BY porcentaje DESC, total DESC
        LIMIT 3
    """, nativeQuery = true)
    List<Object[]> top3Abandono();

    @Query(value = """
        SELECT 
            p.id_producto,
            p.nombre_producto,
            COALESCE(pm.total_vistas, 0),
            COALESCE(pm.total_clicks, 0),
            COALESCE(pm.total_whatsapp, 0),
            COALESCE(pm.total_consultas, 0),
            COALESCE(pm.total_ventas, 0),
            CASE 
                WHEN COALESCE(pm.total_vistas, 0) = 0 THEN 0
                ELSE ROUND(
                    ((COALESCE(pm.total_whatsapp, 0) + COALESCE(pm.total_consultas, 0) + COALESCE(pm.total_ventas, 0)) 
                    / COALESCE(pm.total_vistas, 1)) * 100, 2
                )
            END
        FROM productos p
        LEFT JOIN producto_metricas pm ON pm.id_producto = p.id_producto
        WHERE p.estado_producto = 'ACTIVO'
        ORDER BY 8 DESC
        LIMIT 10
    """, nativeQuery = true)
    List<Object[]> productosConMayorConversion();

    @Query(value = """
        SELECT 
            p.id_producto,
            p.nombre_producto,
            COALESCE(pm.total_vistas, 0),
            COALESCE(pm.total_clicks, 0),
            COALESCE(pm.total_whatsapp, 0),
            COALESCE(pm.total_consultas, 0),
            COALESCE(pm.total_ventas, 0),
            CASE 
                WHEN COALESCE(pm.total_vistas, 0) = 0 THEN 0
                ELSE ROUND(
                    ((COALESCE(pm.total_whatsapp, 0) + COALESCE(pm.total_consultas, 0) + COALESCE(pm.total_ventas, 0)) 
                    / COALESCE(pm.total_vistas, 1)) * 100, 2
                )
            END
        FROM productos p
        LEFT JOIN producto_metricas pm ON pm.id_producto = p.id_producto
        WHERE p.estado_producto = 'ACTIVO'
          AND COALESCE(pm.total_vistas, 0) >= 5
          AND COALESCE(pm.total_whatsapp, 0) = 0
          AND COALESCE(pm.total_consultas, 0) = 0
        ORDER BY COALESCE(pm.total_vistas, 0) DESC
        LIMIT 10
    """, nativeQuery = true)
    List<Object[]> productosConMuchasVistasSinConversion();

    @Query(value = """
        SELECT 
            p.id_producto,
            p.nombre_producto,
            COALESCE(pm.total_vistas, 0),
            COALESCE(pm.total_clicks, 0),
            COALESCE(pm.total_whatsapp, 0),
            COALESCE(pm.total_consultas, 0)
        FROM productos p
        LEFT JOIN producto_metricas pm ON pm.id_producto = p.id_producto
        WHERE p.estado_producto = 'ACTIVO'
          AND COALESCE(pm.total_vistas, 0) >= 5
          AND COALESCE(pm.total_whatsapp, 0) = 0
          AND COALESCE(pm.total_consultas, 0) = 0
        ORDER BY COALESCE(pm.total_vistas, 0) DESC
        LIMIT 10
    """, nativeQuery = true)
    List<Object[]> productosConAbandonoRaw();

    default List<ProductoAbandonoResponse> productosConAbandono() {
        return productosConAbandonoRaw()
                .stream()
                .map(fila -> new ProductoAbandonoResponse(
                        ((Number) fila[0]).longValue(),
                        String.valueOf(fila[1]),
                        ((Number) fila[2]).longValue(),
                        ((Number) fila[3]).longValue(),
                        ((Number) fila[4]).longValue(),
                        ((Number) fila[5]).longValue()
                ))
                .toList();
    }
}
