package com.LusoSAC.Sistema_Ecommerce.service;

import com.LusoSAC.Sistema_Ecommerce.dto.dashboard.InteraccionRequest;
import com.LusoSAC.Sistema_Ecommerce.model.Interaccion;
import com.LusoSAC.Sistema_Ecommerce.model.Usuario;
import com.LusoSAC.Sistema_Ecommerce.model.Visitante;
import com.LusoSAC.Sistema_Ecommerce.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InteraccionService {

    private final InteraccionRepository interaccionRepository;
    private final ProductoRepository productoRepository;
    private final ServicioRepository servicioRepository;
    private final UsuarioRepository usuarioRepository;
    private final VisitanteRepository visitanteRepository;
    private final ProductoMetricaService productoMetricaService;

    public InteraccionService(
            InteraccionRepository interaccionRepository,
            ProductoRepository productoRepository,
            ServicioRepository servicioRepository,
            UsuarioRepository usuarioRepository,
            VisitanteRepository visitanteRepository,
            ProductoMetricaService productoMetricaService
    ) {
        this.interaccionRepository = interaccionRepository;
        this.productoRepository = productoRepository;
        this.servicioRepository = servicioRepository;
        this.usuarioRepository = usuarioRepository;
        this.visitanteRepository = visitanteRepository;
        this.productoMetricaService = productoMetricaService;
    }

    public Interaccion registrar(InteraccionRequest request) {
        if (request == null || request.getTipo() == null || request.getTipo().isBlank()) {
            return null;
        }

        Usuario usuario = obtenerUsuario(request.getIdUsuario());

        if (usuario != null && esAdmin(usuario)) {
            return null;
        }

        Interaccion interaccion = new Interaccion();

        if (request.getIdProducto() != null) {
            productoRepository.findById(request.getIdProducto())
                    .ifPresent(interaccion::setProducto);
        }

        if (request.getIdServicio() != null) {
            servicioRepository.findById(request.getIdServicio())
                    .ifPresent(interaccion::setServicio);
        }

        if (usuario != null) {
            interaccion.setUsuario(usuario);
            interaccion.setRolOrigen("USUARIO");
            interaccion.setSessionId(null);
            interaccion.setVisitante(null);
        } else {
            Visitante visitante = obtenerOCrearVisitante(request);

            if (visitante != null) {
                interaccion.setVisitante(visitante);
                interaccion.setSessionId(visitante.getSessionId());
            } else {
                interaccion.setSessionId(limpiar(request.getSessionId()));
            }

            interaccion.setRolOrigen("VISITANTE");
        }

        String tipoNormalizado = normalizar(request.getTipo());

        interaccion.setTipo(tipoNormalizado);
        interaccion.setOrigenPagina(normalizarOrigenPagina(request.getOrigenPagina()));
        interaccion.setDetalle(request.getDetalle());
        interaccion.setIpAddress(request.getIpAddress());
        interaccion.setUserAgent(request.getUserAgent());

        Interaccion guardada = interaccionRepository.save(interaccion);

        if (request.getIdProducto() != null) {
            productoMetricaService.registrarInteraccionProducto(request.getIdProducto(), tipoNormalizado);
        }

        return guardada;
    }

    public List<Interaccion> listarUltimas() {
        try {
            return interaccionRepository.findTop20ByOrderByIdDesc();
        } catch (Exception e) {
            return List.of();
        }
    }

    private Usuario obtenerUsuario(Long idUsuario) {
        if (idUsuario == null || idUsuario <= 0) {
            return null;
        }

        return usuarioRepository.findById(idUsuario).orElse(null);
    }

    private Visitante obtenerOCrearVisitante(InteraccionRequest request) {
        if (request.getIdVisitante() != null && request.getIdVisitante() > 0) {
            return visitanteRepository.findById(request.getIdVisitante()).orElse(null);
        }

        String sessionId = limpiar(request.getSessionId());

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
                    nuevo.setIpAddress(request.getIpAddress());
                    nuevo.setUserAgent(request.getUserAgent());
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

    private String normalizar(String tipo) {
        return tipo == null ? null : tipo.trim().toLowerCase();
    }

    private String normalizarOrigenPagina(String origenPagina) {
        if (origenPagina == null || origenPagina.isBlank()) {
            return null;
        }

        return origenPagina.trim().toUpperCase();
    }

    private String limpiar(String texto) {
        if (texto == null || texto.isBlank()) {
            return null;
        }

        return texto.trim();
    }
}
