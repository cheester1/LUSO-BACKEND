package com.LusoSAC.Sistema_Ecommerce.controller;

import com.LusoSAC.Sistema_Ecommerce.model.ProductoMetrica;
import com.LusoSAC.Sistema_Ecommerce.repository.ProductoMetricaRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/producto-metricas")
public class ProductoMetricaController {

    private final ProductoMetricaRepository repository;

    public ProductoMetricaController(ProductoMetricaRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/todos")
    public List<ProductoMetrica> listarTodos() {
        return repository.findAll();
    }

    @PostMapping
    public ProductoMetrica guardar(@RequestBody ProductoMetrica metrica) {
        return repository.save(metrica);
    }
}