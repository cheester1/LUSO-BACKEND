package com.LusoSAC.Sistema_Ecommerce.repository;

import com.LusoSAC.Sistema_Ecommerce.model.ProductoMetricaDiaria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ProductoMetricaDiariaRepository extends JpaRepository<ProductoMetricaDiaria, Long> {

    Optional<ProductoMetricaDiaria> findByIdProductoAndFecha(Long idProducto, LocalDate fecha);
}
