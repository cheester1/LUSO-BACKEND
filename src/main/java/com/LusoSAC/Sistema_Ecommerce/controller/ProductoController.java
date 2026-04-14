package com.LusoSAC.Sistema_Ecommerce.controller;

import com.LusoSAC.Sistema_Ecommerce.model.Producto;
import com.LusoSAC.Sistema_Ecommerce.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productos")
@CrossOrigin
public class ProductoController {

    @Autowired
    private ProductoRepository repo;

    // 1. LISTAR TODOS
    @GetMapping("/todos")
    public List<Producto> listarTodos() {
        return repo.findAll();
    }

    // 2. 10 PRODUCTOS ALEATORIOS
    @GetMapping("/random")
    public List<Producto> random() {
        return repo.obtenerAleatorios();
    }

    // 3. PAGINACIÓN (PRO)
    @GetMapping
    public Page<Producto> listarPaginado(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return repo.findAll(pageable);
    }

    // 4. BUSCAR POR ID
    @GetMapping("/{id}")
    public Producto obtener(@PathVariable Long id) {
        return repo.findById(id).orElse(null);
    }
}