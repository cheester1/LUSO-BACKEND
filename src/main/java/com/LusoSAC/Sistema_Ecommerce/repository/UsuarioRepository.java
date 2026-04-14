package com.LusoSAC.Sistema_Ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.LusoSAC.Sistema_Ecommerce.model.*;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {}