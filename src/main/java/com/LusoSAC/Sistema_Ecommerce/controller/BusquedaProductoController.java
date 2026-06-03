package com.LusoSAC.Sistema_Ecommerce.controller;

import com.LusoSAC.Sistema_Ecommerce.model.BusquedaProducto;
import com.LusoSAC.Sistema_Ecommerce.repository.BusquedaProductoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/busquedas-producto")
public class BusquedaProductoController {

    private final BusquedaProductoRepository repository;

    public BusquedaProductoController(BusquedaProductoRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/todos")
    public List<BusquedaProducto> listarTodos() {
        return repository.findAll();
    }

    @GetMapping("/session/{sessionId}")
    public List<BusquedaProducto> listarPorSession(@PathVariable String sessionId) {
        return repository.findBySessionId(sessionId);
    }
}