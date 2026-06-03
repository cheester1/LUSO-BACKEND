package com.LusoSAC.Sistema_Ecommerce.controller;

import com.LusoSAC.Sistema_Ecommerce.model.ProductoImagen;
import com.LusoSAC.Sistema_Ecommerce.repository.ProductoImagenRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/producto-imagenes")
public class ProductoImagenController {

    private final ProductoImagenRepository productoImagenRepository;

    public ProductoImagenController(ProductoImagenRepository productoImagenRepository) {
        this.productoImagenRepository = productoImagenRepository;
    }

    @GetMapping("/producto/{id}")
    public List<ProductoImagen> listarPorProducto(@PathVariable Long id) {
        return productoImagenRepository.findByProductoId(id);
    }
}