package com.LusoSAC.Sistema_Ecommerce.repository;

import com.LusoSAC.Sistema_Ecommerce.model.BusquedaProducto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusquedaProductoRepository extends JpaRepository<BusquedaProducto, Long> {

    List<BusquedaProducto> findBySessionId(String sessionId);

    @Query("""
        SELECT b.terminoBusqueda
        FROM BusquedaProducto b
        GROUP BY b.terminoBusqueda
        ORDER BY COUNT(b) DESC
    """)
    List<String> terminosMasBuscados(Pageable pageable);
}