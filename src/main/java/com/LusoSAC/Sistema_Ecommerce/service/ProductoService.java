package com.LusoSAC.Sistema_Ecommerce.service;

import com.LusoSAC.Sistema_Ecommerce.dto.producto.ProductoFiltroRequest;
import com.LusoSAC.Sistema_Ecommerce.dto.producto.ProductoFiltroResponse;
import com.LusoSAC.Sistema_Ecommerce.dto.producto.ProductoCardResponse;
import com.LusoSAC.Sistema_Ecommerce.repository.ProductoRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public ProductoFiltroResponse filtrarProductos(ProductoFiltroRequest request) {

        if (request == null) {
            request = new ProductoFiltroRequest();
        }

        int page = request.getPage() == null ? 0 : Math.max(request.getPage(), 0);
        int size = request.getSize() == null ? 12 : request.getSize();

        size = Math.min(Math.max(size, 1), 60);

        Pageable pageable = PageRequest.of(page, size, construirSort(request.getSort()));

        Page<ProductoCardResponse> resultado = productoRepository.filtrarProductosCards(
                limpiar(request.getQ()),
                request.getIdCategoria(),
                request.getIdMarca(),
                limpiar(request.getModelo()),
                request.getPrecioMin(),
                request.getPrecioMax(),
                pageable
        );

        return new ProductoFiltroResponse(resultado);
    }

    private Sort construirSort(String sort) {
        if (sort == null || sort.isBlank()) {
            return Sort.by(Sort.Direction.DESC, "id");
        }

        return switch (sort.trim().toLowerCase()) {
            case "precio_asc" -> Sort.by("precio").ascending();
            case "precio_desc" -> Sort.by("precio").descending();
            case "nombre_asc" -> Sort.by("nombre").ascending();
            case "nombre_desc" -> Sort.by("nombre").descending();
            case "stock_desc" -> Sort.by("stock").descending();
            case "recientes" -> Sort.by("id").descending();
            default -> Sort.by("id").descending();
        };
    }

    private String limpiar(String texto) {
        if (texto == null || texto.isBlank()) {
            return null;
        }

        return texto.trim();
    }
}