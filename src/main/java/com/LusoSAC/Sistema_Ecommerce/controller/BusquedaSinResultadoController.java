package com.LusoSAC.Sistema_Ecommerce.controller;

import com.LusoSAC.Sistema_Ecommerce.model.BusquedaSinResultado;
import com.LusoSAC.Sistema_Ecommerce.repository.BusquedaSinResultadoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/busquedas-sin-resultado")
public class BusquedaSinResultadoController {

    private final BusquedaSinResultadoRepository repository;

    public BusquedaSinResultadoController(BusquedaSinResultadoRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/todos")
    public List<BusquedaSinResultado> listarTodos() {
        return repository.findAll();
    }

    @GetMapping("/session/{sessionId}")
    public List<BusquedaSinResultado> listarPorSession(@PathVariable String sessionId) {
        return repository.findBySessionId(sessionId);
    }

    @PostMapping
    public BusquedaSinResultado guardar(@RequestBody BusquedaSinResultado busqueda) {
        return repository.save(busqueda);
    }
}