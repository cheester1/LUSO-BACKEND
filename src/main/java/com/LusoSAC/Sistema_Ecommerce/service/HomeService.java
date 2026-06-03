package com.LusoSAC.Sistema_Ecommerce.service;

import com.LusoSAC.Sistema_Ecommerce.dto.home.HomeResponse;
import com.LusoSAC.Sistema_Ecommerce.dto.producto.ProductoCardResponse;
import com.LusoSAC.Sistema_Ecommerce.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeService {

    private final ProductoRepository productoRepository;

    public HomeService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public HomeResponse obtenerHome() {

        List<ProductoCardResponse> destacados =
                productoRepository.obtenerProductosDestacadosCards();

        List<ProductoCardResponse> recomendados =
                productoRepository.obtenerProductosRecomendadosCards();

        List<ProductoCardResponse> populares =
                productoRepository.obtenerProductosPopularesCards();

        if (destacados.isEmpty()) {
            destacados = productoRepository.listarCards()
                    .stream()
                    .limit(10)
                    .toList();
        }

        if (recomendados.isEmpty()) {
            recomendados = productoRepository.listarCards()
                    .stream()
                    .limit(10)
                    .toList();
        }

        if (populares.isEmpty()) {
            populares = productoRepository.listarCards()
                    .stream()
                    .limit(10)
                    .toList();
        }

        return new HomeResponse(
                destacados,
                recomendados,
                populares
        );
    }
}