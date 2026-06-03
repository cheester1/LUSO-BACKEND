package com.LusoSAC.Sistema_Ecommerce.repository;

import com.LusoSAC.Sistema_Ecommerce.model.Visitante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VisitanteRepository extends JpaRepository<Visitante, Long> {

    Optional<Visitante> findBySessionId(String sessionId);
}