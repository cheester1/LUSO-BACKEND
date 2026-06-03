package com.LusoSAC.Sistema_Ecommerce.controller;

import com.LusoSAC.Sistema_Ecommerce.dto.producto.ProductoCardResponse;
import com.LusoSAC.Sistema_Ecommerce.repository.ProductoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recomendaciones")
public class RecomendacionController {

    private final ProductoRepository productoRepository;

    public RecomendacionController(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @GetMapping("/productos-destacados")
    public List<ProductoCardResponse> productosDestacados() {
        return productoRepository.obtenerProductosDestacadosCards();
    }

    @GetMapping("/productos-recomendados")
    public List<ProductoCardResponse> productosRecomendados() {
        return productoRepository.obtenerProductosRecomendadosCards();
    }

    @GetMapping("/productos-populares")
    public List<ProductoCardResponse> productosPopulares() {
        return productoRepository.obtenerProductosPopularesCards();
    }

    @GetMapping("/general")
    public List<ProductoCardResponse> recomendacionesGenerales() {
        return productoRepository.recomendarGeneralCards();
    }

    @GetMapping("/visitante/{idVisitante}")
    public List<ProductoCardResponse> recomendacionesPorVisitante(@PathVariable Long idVisitante) {
        List<ProductoCardResponse> recomendaciones = productoRepository.recomendarPorVisitanteCards(idVisitante);

        if (recomendaciones.isEmpty()) {
            return productoRepository.recomendarGeneralCards();
        }

        return recomendaciones;
    }
}