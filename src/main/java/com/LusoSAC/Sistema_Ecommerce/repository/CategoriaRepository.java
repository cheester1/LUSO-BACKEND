package com.LusoSAC.Sistema_Ecommerce.repository;

import com.LusoSAC.Sistema_Ecommerce.dto.producto.CategoriaOptionResponse;
import com.LusoSAC.Sistema_Ecommerce.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    @Query("""
        SELECT new com.LusoSAC.Sistema_Ecommerce.dto.producto.CategoriaOptionResponse(
            c.id,
            c.nombre
        )
        FROM Categoria c
        WHERE c.estado = 1
        ORDER BY c.nombre ASC
    """)
    List<CategoriaOptionResponse> listarCategoriasActivas();
}