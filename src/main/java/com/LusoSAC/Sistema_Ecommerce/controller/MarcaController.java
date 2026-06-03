package com.LusoSAC.Sistema_Ecommerce.controller;

import com.LusoSAC.Sistema_Ecommerce.dto.producto.MarcaOptionResponse;
import com.LusoSAC.Sistema_Ecommerce.model.Marca;
import com.LusoSAC.Sistema_Ecommerce.repository.MarcaRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/marcas")
public class MarcaController {

    private final MarcaRepository marcaRepository;

    public MarcaController(MarcaRepository marcaRepository) {
        this.marcaRepository = marcaRepository;
    }

    @GetMapping
    public List<MarcaOptionResponse> listarActivas() {
        return marcaRepository.listarMarcasActivas();
    }

    @GetMapping("/todos")
    public List<Marca> listarTodos() {
        return marcaRepository.findAll();
    }

    @GetMapping("/{id}")
    public Marca obtenerPorId(@PathVariable Long id) {
        return marcaRepository.findById(id).orElse(null);
    }
}