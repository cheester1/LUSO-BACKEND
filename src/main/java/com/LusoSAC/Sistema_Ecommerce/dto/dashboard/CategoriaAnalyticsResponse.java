package com.LusoSAC.Sistema_Ecommerce.dto.dashboard;

public class CategoriaAnalyticsResponse {

    private String categoria;
    private Long totalInteracciones;

    public CategoriaAnalyticsResponse(String categoria, Long totalInteracciones) {
        this.categoria = categoria;
        this.totalInteracciones = totalInteracciones;
    }

    public String getCategoria() {
        return categoria;
    }

    public Long getTotalInteracciones() {
        return totalInteracciones;
    }
}