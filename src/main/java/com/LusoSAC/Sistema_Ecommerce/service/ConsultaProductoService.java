package com.LusoSAC.Sistema_Ecommerce.service;

import com.LusoSAC.Sistema_Ecommerce.model.ConsultaProducto;
import com.LusoSAC.Sistema_Ecommerce.model.Usuario;
import com.LusoSAC.Sistema_Ecommerce.model.Visitante;
import com.LusoSAC.Sistema_Ecommerce.repository.ConsultaProductoRepository;
import com.LusoSAC.Sistema_Ecommerce.repository.UsuarioRepository;
import com.LusoSAC.Sistema_Ecommerce.repository.VisitanteRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ConsultaProductoService {

    private final ConsultaProductoRepository consultaProductoRepository;
    private final UsuarioRepository usuarioRepository;
    private final VisitanteRepository visitanteRepository;

    public ConsultaProductoService(
            ConsultaProductoRepository consultaProductoRepository,
            UsuarioRepository usuarioRepository,
            VisitanteRepository visitanteRepository
    ) {
        this.consultaProductoRepository = consultaProductoRepository;
        this.usuarioRepository = usuarioRepository;
        this.visitanteRepository = visitanteRepository;
    }

    public ConsultaProducto registrar(ConsultaProducto consultaProducto) {
        if (consultaProducto == null || consultaProducto.getIdProducto() == null) {
            throw new IllegalArgumentException("El producto es obligatorio");
        }

        Usuario usuario = obtenerUsuario(consultaProducto.getIdUsuario());

        if (usuario != null && esAdmin(usuario)) {
            return null;
        }

        if (usuario != null) {
            consultaProducto.setIdUsuario(usuario.getId());
            consultaProducto.setIdVisitante(null);
            consultaProducto.setSessionId(null);
        } else {
            Visitante visitante = obtenerOCrearVisitante(consultaProducto);

            if (visitante != null) {
                consultaProducto.setIdVisitante(visitante.getId());
                consultaProducto.setSessionId(visitante.getSessionId());
            }

            consultaProducto.setIdUsuario(null);
        }

        consultaProducto.setTipoConsulta(normalizarTipoConsulta(consultaProducto.getTipoConsulta()));

        if (consultaProducto.getEstadoConsulta() == null || consultaProducto.getEstadoConsulta().isBlank()) {
            consultaProducto.setEstadoConsulta("PENDIENTE");
        }

        return consultaProductoRepository.save(consultaProducto);
    }

    private Usuario obtenerUsuario(Long idUsuario) {
        if (idUsuario == null || idUsuario <= 0) {
            return null;
        }

        return usuarioRepository.findById(idUsuario).orElse(null);
    }

    private Visitante obtenerOCrearVisitante(ConsultaProducto consultaProducto) {
        if (consultaProducto.getIdVisitante() != null && consultaProducto.getIdVisitante() > 0) {
            return visitanteRepository.findById(consultaProducto.getIdVisitante()).orElse(null);
        }

        String sessionId = limpiar(consultaProducto.getSessionId());

        if (sessionId == null) {
            return null;
        }

        return visitanteRepository.findBySessionId(sessionId)
                .map(visitante -> {
                    visitante.setFechaUltimaVisita(LocalDateTime.now());
                    return visitanteRepository.save(visitante);
                })
                .orElseGet(() -> {
                    Visitante nuevo = new Visitante();
                    nuevo.setSessionId(sessionId);
                    nuevo.setFechaPrimeraVisita(LocalDateTime.now());
                    nuevo.setFechaUltimaVisita(LocalDateTime.now());
                    return visitanteRepository.save(nuevo);
                });
    }

    private boolean esAdmin(Usuario usuario) {
        if (usuario.getRol() == null) return false;

        String rol = usuario.getRol().trim().toUpperCase();

        return rol.equals("ADMIN") || rol.equals("ADMINISTRADOR") || rol.equals("ROLE_ADMINISTRADOR");
    }

    private String normalizarTipoConsulta(String tipoConsulta) {
        if (tipoConsulta == null || tipoConsulta.isBlank()) {
            return "WHATSAPP";
        }

        return tipoConsulta.trim().toUpperCase();
    }

    private String limpiar(String texto) {
        if (texto == null || texto.isBlank()) {
            return null;
        }

        return texto.trim();
    }
}
