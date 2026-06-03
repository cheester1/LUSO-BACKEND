package com.LusoSAC.Sistema_Ecommerce.repository;

import com.LusoSAC.Sistema_Ecommerce.dto.dashboard.CategoriaAnalyticsResponse;
import com.LusoSAC.Sistema_Ecommerce.dto.dashboard.InteraccionTipoResponse;
import com.LusoSAC.Sistema_Ecommerce.dto.dashboard.ProductoRankingResponse;
import com.LusoSAC.Sistema_Ecommerce.dto.dashboard.VisitanteInteresResponse;
import com.LusoSAC.Sistema_Ecommerce.model.Interaccion;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InteraccionRepository extends JpaRepository<Interaccion, Long> {

    List<Interaccion> findTop20ByOrderByIdDesc();

    @Query("""
        SELECT new com.LusoSAC.Sistema_Ecommerce.dto.dashboard.InteraccionTipoResponse(
            i.tipo,
            COUNT(i)
        )
        FROM Interaccion i
        GROUP BY i.tipo
        ORDER BY COUNT(i) DESC
    """)
    List<InteraccionTipoResponse> contarPorTipo();

    @Query("""
        SELECT new com.LusoSAC.Sistema_Ecommerce.dto.dashboard.ProductoRankingResponse(
            p.id,
            p.nombre,
            p.modelo,
            p.precio,
            COUNT(i)
        )
        FROM Interaccion i
        JOIN i.producto p
        WHERE i.producto IS NOT NULL
        GROUP BY p.id, p.nombre, p.modelo, p.precio
        ORDER BY COUNT(i) DESC
    """)
    List<ProductoRankingResponse> productosMasInteractuados(Pageable pageable);

    @Query("""
        SELECT new com.LusoSAC.Sistema_Ecommerce.dto.dashboard.ProductoRankingResponse(
            p.id,
            p.nombre,
            p.modelo,
            p.precio,
            COUNT(i)
        )
        FROM Interaccion i
        JOIN i.producto p
        WHERE i.producto IS NOT NULL
          AND LOWER(i.tipo) = LOWER(:tipo)
        GROUP BY p.id, p.nombre, p.modelo, p.precio
        ORDER BY COUNT(i) DESC
    """)
    List<ProductoRankingResponse> productosPorTipoInteraccion(String tipo, Pageable pageable);

    @Query("""
        SELECT new com.LusoSAC.Sistema_Ecommerce.dto.dashboard.CategoriaAnalyticsResponse(
            p.categoria.nombre,
            COUNT(i)
        )
        FROM Interaccion i
        JOIN i.producto p
        WHERE i.producto IS NOT NULL
          AND p.categoria IS NOT NULL
        GROUP BY p.categoria.nombre
        ORDER BY COUNT(i) DESC
    """)
    List<CategoriaAnalyticsResponse> categoriasMasInteresadas();

    @Query("""
        SELECT new com.LusoSAC.Sistema_Ecommerce.dto.dashboard.VisitanteInteresResponse(
            v.id,
            v.sessionId,
            p.categoria.nombre,
            COUNT(i)
        )
        FROM Interaccion i
        JOIN i.visitante v
        JOIN i.producto p
        WHERE i.visitante IS NOT NULL
          AND i.producto IS NOT NULL
          AND p.categoria IS NOT NULL
        GROUP BY v.id, v.sessionId, p.categoria.nombre
        ORDER BY v.id ASC, COUNT(i) DESC
    """)
    List<VisitanteInteresResponse> interesesPorVisitante();
}