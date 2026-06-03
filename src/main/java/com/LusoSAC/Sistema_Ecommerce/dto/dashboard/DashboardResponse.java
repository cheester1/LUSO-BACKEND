package com.LusoSAC.Sistema_Ecommerce.dto.dashboard;

import com.LusoSAC.Sistema_Ecommerce.model.ProductoMetrica;
import java.util.List;

public class DashboardResponse {

    private List<ProductoMetrica> scoreProductos;
    private List<String> busquedasMasRealizadas;
    private List<ProductoConversionResponse> productosMayorConversion;
    private List<ProductoAbandonoResponse> productosAbandono;
    private List<CategoriaAnalyticsResponse> categoriasMasInteres;
    private List<VisitanteInteresResponse> visitantesIntereses;

    public DashboardResponse(
            List<ProductoMetrica> scoreProductos,
            List<String> busquedasMasRealizadas,
            List<ProductoConversionResponse> productosMayorConversion,
            List<ProductoAbandonoResponse> productosAbandono,
            List<CategoriaAnalyticsResponse> categoriasMasInteres,
            List<VisitanteInteresResponse> visitantesIntereses
    ) {
        this.scoreProductos = scoreProductos;
        this.busquedasMasRealizadas = busquedasMasRealizadas;
        this.productosMayorConversion = productosMayorConversion;
        this.productosAbandono = productosAbandono;
        this.categoriasMasInteres = categoriasMasInteres;
        this.visitantesIntereses = visitantesIntereses;
    }

    public List<ProductoMetrica> getScoreProductos() { return scoreProductos; }
    public List<String> getBusquedasMasRealizadas() { return busquedasMasRealizadas; }
    public List<ProductoConversionResponse> getProductosMayorConversion() { return productosMayorConversion; }
    public List<ProductoAbandonoResponse> getProductosAbandono() { return productosAbandono; }
    public List<CategoriaAnalyticsResponse> getCategoriasMasInteres() { return categoriasMasInteres; }
    public List<VisitanteInteresResponse> getVisitantesIntereses() { return visitantesIntereses; }
}