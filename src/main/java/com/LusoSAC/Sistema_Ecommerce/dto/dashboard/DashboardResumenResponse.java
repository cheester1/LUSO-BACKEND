package com.LusoSAC.Sistema_Ecommerce.dto.dashboard;

public class DashboardResumenResponse {

    private Long totalProductos;
    private Long totalInteracciones;
    private Long totalFavoritos;
    private Long totalConsultas;
    private Long totalBusquedasSinResultado;

    public DashboardResumenResponse(
            Long totalProductos,
            Long totalInteracciones,
            Long totalFavoritos,
            Long totalConsultas,
            Long totalBusquedasSinResultado
    ) {
        this.totalProductos = totalProductos;
        this.totalInteracciones = totalInteracciones;
        this.totalFavoritos = totalFavoritos;
        this.totalConsultas = totalConsultas;
        this.totalBusquedasSinResultado = totalBusquedasSinResultado;
    }

    public Long getTotalProductos() { return totalProductos; }
    public Long getTotalInteracciones() { return totalInteracciones; }
    public Long getTotalFavoritos() { return totalFavoritos; }
    public Long getTotalConsultas() { return totalConsultas; }
    public Long getTotalBusquedasSinResultado() { return totalBusquedasSinResultado; }
}