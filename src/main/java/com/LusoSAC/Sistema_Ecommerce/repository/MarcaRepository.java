package com.LusoSAC.Sistema_Ecommerce.repository;

import com.LusoSAC.Sistema_Ecommerce.dto.producto.MarcaOptionResponse;
import com.LusoSAC.Sistema_Ecommerce.model.Marca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MarcaRepository extends JpaRepository<Marca, Long> {

    @Query("""
        SELECT new com.LusoSAC.Sistema_Ecommerce.dto.producto.MarcaOptionResponse(
            m.id,
            m.nombre
        )
        FROM Marca m
        WHERE m.estado = 1
        ORDER BY m.nombre ASC
    """)
    List<MarcaOptionResponse> listarMarcasActivas();
}