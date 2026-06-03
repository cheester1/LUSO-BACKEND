package com.LusoSAC.Sistema_Ecommerce.repository;

import com.LusoSAC.Sistema_Ecommerce.model.BusquedaSinResultado;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusquedaSinResultadoRepository extends JpaRepository<BusquedaSinResultado, Long> {

    List<BusquedaSinResultado> findBySessionId(String sessionId);

    @Query("""
        SELECT b.terminoBusqueda
        FROM BusquedaSinResultado b
        GROUP BY b.terminoBusqueda
        ORDER BY COUNT(b) DESC
    """)
    List<String> terminosMasBuscadosSinResultado(Pageable pageable);
}