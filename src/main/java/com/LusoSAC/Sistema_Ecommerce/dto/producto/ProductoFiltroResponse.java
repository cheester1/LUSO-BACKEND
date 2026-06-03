package com.LusoSAC.Sistema_Ecommerce.dto.producto;

import org.springframework.data.domain.Page;

import java.util.List;

public class ProductoFiltroResponse {

    private List<ProductoCardResponse> productos;
    private int paginaActual;
    private int totalPaginas;
    private long totalElementos;
    private int tamanioPagina;
    private boolean primeraPagina;
    private boolean ultimaPagina;

    public ProductoFiltroResponse(Page<ProductoCardResponse> page) {

        this.productos = page.getContent();

        this.paginaActual = page.getNumber();
        this.totalPaginas = page.getTotalPages();
        this.totalElementos = page.getTotalElements();
        this.tamanioPagina = page.getSize();
        this.primeraPagina = page.isFirst();
        this.ultimaPagina = page.isLast();
    }

    public List<ProductoCardResponse> getProductos() {
        return productos;
    }

    public int getPaginaActual() {
        return paginaActual;
    }

    public int getTotalPaginas() {
        return totalPaginas;
    }

    public long getTotalElementos() {
        return totalElementos;
    }

    public int getTamanioPagina() {
        return tamanioPagina;
    }

    public boolean isPrimeraPagina() {
        return primeraPagina;
    }

    public boolean isUltimaPagina() {
        return ultimaPagina;
    }
}