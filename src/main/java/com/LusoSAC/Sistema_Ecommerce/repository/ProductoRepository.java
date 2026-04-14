package com.LusoSAC.Sistema_Ecommerce.repository;

import com.LusoSAC.Sistema_Ecommerce.model.Producto;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // 🔥 10 productos aleatorios
    @Query(value = "SELECT * FROM productos ORDER BY RAND() LIMIT 10", nativeQuery = true)
    List<Producto> obtenerAleatorios();
}