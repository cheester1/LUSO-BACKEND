package com.LusoSAC.Sistema_Ecommerce.controller;

import com.LusoSAC.Sistema_Ecommerce.dto.producto.CategoriaOptionResponse;
import com.LusoSAC.Sistema_Ecommerce.model.Categoria;
import com.LusoSAC.Sistema_Ecommerce.repository.CategoriaRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final CategoriaRepository categoriaRepository;

    public CategoriaController(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @GetMapping
    public List<CategoriaOptionResponse> listarActivas() {
        return categoriaRepository.listarCategoriasActivas();
    }

    @GetMapping("/todos")
    public List<Categoria> listarTodos() {
        return categoriaRepository.findAll();
    }

    @GetMapping("/{id}")
    public Categoria obtenerPorId(@PathVariable Long id) {
        return categoriaRepository.findById(id).orElse(null);
    }
}