package com.LusoSAC.Sistema_Ecommerce.controller;

import com.LusoSAC.Sistema_Ecommerce.dto.dashboard.*;
import com.LusoSAC.Sistema_Ecommerce.model.ProductoMetrica;
import com.LusoSAC.Sistema_Ecommerce.service.DashboardService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/resumen")
    public DashboardResumenResponse resumen() {
        return dashboardService.obtenerResumen();
    }

    @GetMapping("/completo")
    public DashboardResponse dashboardCompleto() {
        return dashboardService.obtenerDashboard();
    }

    @GetMapping("/interacciones-por-tipo")
    public List<InteraccionTipoResponse> interaccionesPorTipo() {
        return dashboardService.obtenerInteraccionesPorTipo();
    }

    @GetMapping("/productos-mas-interactuados")
    public List<ProductoRankingResponse> productosMasInteractuados() {
        return dashboardService.obtenerProductosMasInteractuados();
    }

    @GetMapping("/productos-mas-favoritos")
    public List<ProductoRankingResponse> productosMasFavoritos() {
        return dashboardService.obtenerProductosMasFavoritos();
    }

    @GetMapping("/productos-mas-vistos")
    public List<ProductoRankingResponse> productosMasVistos() {
        return dashboardService.obtenerProductosMasVistos();
    }

    @GetMapping("/productos-mas-clickeados")
    public List<ProductoRankingResponse> productosMasClickeados() {
        return dashboardService.obtenerProductosMasClickeados();
    }

    @GetMapping("/busquedas-sin-resultado")
    public List<String> busquedasSinResultado() {
        return dashboardService.obtenerBusquedasSinResultado();
    }

    @GetMapping("/metricas/score")
    public List<ProductoMetrica> productosPorScore() {
        return dashboardService.obtenerProductosPorScore();
    }

    @GetMapping("/metricas/vistas")
    public List<ProductoMetrica> productosPorVistas() {
        return dashboardService.obtenerProductosPorVistas();
    }

    @GetMapping("/metricas/clicks")
    public List<ProductoMetrica> productosPorClicks() {
        return dashboardService.obtenerProductosPorClicks();
    }

    @GetMapping("/metricas/whatsapp")
    public List<ProductoMetrica> productosPorWhatsapp() {
        return dashboardService.obtenerProductosPorWhatsapp();
    }

    @GetMapping("/productos-mayor-conversion")
    public List<ProductoConversionResponse> productosMayorConversion() {
        return dashboardService.obtenerProductosMayorConversion();
    }

    @GetMapping("/productos-abandono")
    public List<ProductoAbandonoResponse> productosAbandono() {
        return dashboardService.obtenerProductosAbandono();
    }

    @GetMapping("/categorias-mas-interes")
    public List<CategoriaAnalyticsResponse> categoriasMasInteres() {
        return dashboardService.obtenerCategoriasMasInteres();
    }

    @GetMapping("/visitantes-intereses")
    public List<VisitanteInteresResponse> visitantesIntereses() {
        return dashboardService.obtenerVisitantesIntereses();
    }
}