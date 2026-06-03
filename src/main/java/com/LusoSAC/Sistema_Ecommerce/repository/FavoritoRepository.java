package com.LusoSAC.Sistema_Ecommerce.repository;

import com.LusoSAC.Sistema_Ecommerce.dto.dashboard.ProductoRankingResponse;
import com.LusoSAC.Sistema_Ecommerce.model.Favorito;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoritoRepository extends JpaRepository<Favorito, Long> {

    List<Favorito> findByIdUsuario(Long idUsuario);

    Optional<Favorito> findByIdProductoAndIdUsuario(Long idProducto, Long idUsuario);

    boolean existsByIdProductoAndIdUsuario(Long idProducto, Long idUsuario);

    @Query("""
        SELECT new com.LusoSAC.Sistema_Ecommerce.dto.dashboard.ProductoRankingResponse(
            p.id,
            p.nombre,
            p.modelo,
            p.precio,
            COUNT(f)
        )
        FROM Favorito f
        JOIN Producto p ON p.id = f.idProducto
        GROUP BY p.id, p.nombre, p.modelo, p.precio
        ORDER BY COUNT(f) DESC
    """)
    List<ProductoRankingResponse> productosMasFavoritos(Pageable pageable);

    @Query("""
        SELECT new com.LusoSAC.Sistema_Ecommerce.dto.dashboard.ProductoRankingResponse(
            p.id,
            p.nombre,
            p.modelo,
            p.precio,
            COUNT(f)
        )
        FROM Favorito f
        JOIN Producto p ON p.id = f.idProducto
        WHERE f.idUsuario = :idUsuario
        GROUP BY p.id, p.nombre, p.modelo, p.precio
        ORDER BY COUNT(f) DESC, MAX(f.fechaCreacion) DESC
    """)
    List<ProductoRankingResponse> productosMasFavoritosPorUsuario(@Param("idUsuario") Long idUsuario, Pageable pageable);
}
