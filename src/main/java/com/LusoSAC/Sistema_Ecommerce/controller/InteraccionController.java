package com.LusoSAC.Sistema_Ecommerce.controller;

import com.LusoSAC.Sistema_Ecommerce.dto.dashboard.InteraccionRequest;
import com.LusoSAC.Sistema_Ecommerce.model.Interaccion;
import com.LusoSAC.Sistema_Ecommerce.service.InteraccionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/interacciones")
public class InteraccionController {

    private final InteraccionService interaccionService;

    public InteraccionController(InteraccionService interaccionService) {
        this.interaccionService = interaccionService;
    }

    @PostMapping
    public ResponseEntity<Interaccion> guardar(@RequestBody InteraccionRequest request) {

        if (request == null) {
            return ResponseEntity.badRequest().build();
        }

        if (request.getTipo() == null || request.getTipo().trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        if (esAdministrador(request.getRol())) {
            return ResponseEntity.noContent().build();
        }

        request.setTipo(request.getTipo().trim().toLowerCase());

        Interaccion guardada = interaccionService.registrar(request);

        return ResponseEntity.ok(guardada);
    }

    @GetMapping("/ultimas")
    public ResponseEntity<List<Interaccion>> ultimas() {
        return ResponseEntity.ok(interaccionService.listarUltimas());
    }

    private boolean esAdministrador(String rol) {

        if (rol == null || rol.isBlank()) {
            return false;
        }

        String value = rol.trim().toUpperCase();

        return value.equals("ADMIN")
                || value.equals("ADMINISTRADOR")
                || value.equals("ROLE_ADMINISTRADOR");
    }
}