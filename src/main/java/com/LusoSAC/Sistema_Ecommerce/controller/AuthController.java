package com.LusoSAC.Sistema_Ecommerce.controller;

import com.LusoSAC.Sistema_Ecommerce.model.Usuario;
import com.LusoSAC.Sistema_Ecommerce.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UsuarioRepository usuarioRepository;

    public AuthController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("OK");
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {

        if (request == null) {
            return ResponseEntity.badRequest().body("Datos requeridos");
        }

        if (request.nombre() == null || request.nombre().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Nombre requerido");
        }

        if (request.usuario() == null || request.usuario().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Usuario requerido");
        }

        if (request.password() == null || request.password().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Contraseña requerida");
        }

        String nombre = request.nombre().trim();
        String usuarioNormalizado = request.usuario().trim().toLowerCase();
        String password = request.password().trim();

        if (usuarioRepository.existsByUsuario(usuarioNormalizado)) {
            return ResponseEntity.status(409).body("El usuario ya existe");
        }

        Usuario nuevo = new Usuario();
        nuevo.setNombre(nombre);
        nuevo.setUsuario(usuarioNormalizado);
        nuevo.setPasswordHash(password);
        nuevo.setRol("USUARIO");
        nuevo.setEstado(1);

        Usuario guardado = usuarioRepository.save(nuevo);

        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "Usuario registrado correctamente");
        response.put("idUsuario", guardado.getId());
        response.put("nombre", guardado.getNombre());
        response.put("usuario", guardado.getUsuario());
        response.put("rol", normalizarRol(guardado.getRol()));

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        if (request == null) {
            return ResponseEntity.badRequest().body("Usuario y contraseña son obligatorios");
        }

        if (request.usuario() == null || request.usuario().trim().isEmpty()
                || request.password() == null || request.password().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Usuario y contraseña son obligatorios");
        }

        String usuarioNormalizado = request.usuario().trim().toLowerCase();
        String password = request.password().trim();

        Usuario usuario = usuarioRepository.findByUsuario(usuarioNormalizado)
                .orElse(null);

        if (usuario == null) {
            return ResponseEntity.status(401).body("Credenciales incorrectas");
        }

        if (usuario.getEstado() != null && usuario.getEstado() == 0) {
            return ResponseEntity.status(403).body("Usuario inactivo");
        }

        if (usuario.getPasswordHash() == null || !password.equals(usuario.getPasswordHash())) {
            return ResponseEntity.status(401).body("Credenciales incorrectas");
        }

        Map<String, Object> response = new HashMap<>();
        response.put("token", UUID.randomUUID().toString());
        response.put("idUsuario", usuario.getId());
        response.put("nombre", usuario.getNombre() != null ? usuario.getNombre() : "");
        response.put("usuario", usuario.getUsuario() != null ? usuario.getUsuario() : "");
        response.put("rol", normalizarRol(usuario.getRol()));

        return ResponseEntity.ok(response);
    }

    private String normalizarRol(String rol) {
        if (rol == null || rol.trim().isEmpty()) {
            return "USUARIO";
        }

        String value = rol.trim().toUpperCase();

        if (value.equals("ADMIN") || value.equals("ADMINISTRADOR")) {
            return "ADMINISTRADOR";
        }

        return "USUARIO";
    }

    public record LoginRequest(
            String usuario,
            String password
    ) {
    }

    public record RegisterRequest(
            String nombre,
            String usuario,
            String password
    ) {
    }
}