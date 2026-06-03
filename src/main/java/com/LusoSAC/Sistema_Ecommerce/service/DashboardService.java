package com.LusoSAC.Sistema_Ecommerce.service;

import com.LusoSAC.Sistema_Ecommerce.dto.dashboard.*;
import com.LusoSAC.Sistema_Ecommerce.model.ProductoMetrica;
import com.LusoSAC.Sistema_Ecommerce.repository.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class DashboardService {

    private final ProductoRepository productoRepository;
    private final InteraccionRepository interaccionRepository;
    private final FavoritoRepository favoritoRepository;
    private final ConsultaProductoRepository consultaProductoRepository;
    private final BusquedaSinResultadoRepository busquedaSinResultadoRepository;
    private final BusquedaProductoRepository busquedaProductoRepository;
    private final ProductoMetricaRepository productoMetricaRepository;

    public DashboardService(
            ProductoRepository productoRepository,
            InteraccionRepository interaccionRepository,
            FavoritoRepository favoritoRepository,
            ConsultaProductoRepository consultaProductoRepository,
            BusquedaSinResultadoRepository busquedaSinResultadoRepository,
            BusquedaProductoRepository busquedaProductoRepository,
            ProductoMetricaRepository productoMetricaRepository
    ) {
        this.productoRepository = productoRepository;
        this.interaccionRepository = interaccionRepository;
        this.favoritoRepository = favoritoRepository;
        this.consultaProductoRepository = consultaProductoRepository;
        this.busquedaSinResultadoRepository = busquedaSinResultadoRepository;
        this.busquedaProductoRepository = busquedaProductoRepository;
        this.productoMetricaRepository = productoMetricaRepository;
    }

    public DashboardResumenResponse obtenerResumen() {
        return new DashboardResumenResponse(
                productoRepository.count(),
                interaccionRepository.count(),
                favoritoRepository.count(),
                consultaProductoRepository.count(),
                busquedaSinResultadoRepository.count()
        );
    }

    public DashboardResponse obtenerDashboard() {
        return new DashboardResponse(
                obtenerProductosPorScore(),
                obtenerBusquedasMasRealizadas(),
                obtenerProductosMayorConversion(),
                obtenerProductosAbandono(),
                obtenerCategoriasMasInteres(),
                obtenerVisitantesIntereses()
        );
    }

    public List<InteraccionTipoResponse> obtenerInteraccionesPorTipo() {
        return interaccionRepository.contarPorTipo();
    }

    public List<ProductoRankingResponse> obtenerProductosMasInteractuados() {
        return interaccionRepository.productosMasInteractuados(PageRequest.of(0, 10));
    }

    public List<ProductoRankingResponse> obtenerProductosMasFavoritos() {
        return favoritoRepository.productosMasFavoritos(PageRequest.of(0, 10));
    }

    public List<ProductoRankingResponse> obtenerProductosMasFavoritosPorUsuario(Long idUsuario) {
        if (idUsuario == null || idUsuario <= 0) {
            return List.of();
        }

        return favoritoRepository.productosMasFavoritosPorUsuario(idUsuario, PageRequest.of(0, 10));
    }

    public List<ProductoRankingResponse> obtenerProductosMasVistos() {
        return interaccionRepository.productosPorTipoInteraccion("vista", PageRequest.of(0, 10));
    }

    public List<ProductoRankingResponse> obtenerProductosMasClickeados() {
        return interaccionRepository.productosPorTipoInteraccion("click", PageRequest.of(0, 10));
    }

    public List<String> obtenerBusquedasSinResultado() {
        return busquedaSinResultadoRepository.terminosMasBuscadosSinResultado(PageRequest.of(0, 10));
    }

    public List<String> obtenerBusquedasMasRealizadas() {
        return busquedaProductoRepository.terminosMasBuscados(PageRequest.of(0, 10));
    }

    public List<ProductoMetrica> obtenerProductosPorScore() {
        return productoMetricaRepository.findTop10ByOrderByScoreProductoDesc();
    }

    public List<ProductoMetrica> obtenerProductosPorVistas() {
        return productoMetricaRepository.findTop10ByOrderByTotalVistasDesc();
    }

    public List<ProductoMetrica> obtenerProductosPorClicks() {
        return productoMetricaRepository.findTop10ByOrderByTotalClicksDesc();
    }

    public List<ProductoMetrica> obtenerProductosPorWhatsapp() {
        return productoMetricaRepository.findTop10ByOrderByTotalWhatsappDesc();
    }

    public List<ProductoConversionResponse> obtenerProductosMayorConversion() {
        return productoMetricaRepository.productosConMayorConversion()
                .stream()
                .map(this::mapProductoConversion)
                .toList();
    }

    public List<ProductoAbandonoResponse> obtenerProductosAbandono() {
        return productoMetricaRepository.productosConAbandono();
    }

    public List<CategoriaAnalyticsResponse> obtenerCategoriasMasInteres() {
        return interaccionRepository.categoriasMasInteresadas();
    }

    public List<VisitanteInteresResponse> obtenerVisitantesIntereses() {
        return interaccionRepository.interesesPorVisitante();
    }

    public List<ProductoTopDashboardResponse> obtenerTop3Clicks() {
        return mapProductoTop(productoMetricaRepository.top3PorMetrica("clicks"));
    }

    public List<ProductoTopDashboardResponse> obtenerTop3Favoritos() {
        return mapProductoTop(productoMetricaRepository.top3PorMetrica("favoritos"));
    }

    public List<ProductoTopDashboardResponse> obtenerTop3Whatsapp() {
        return mapProductoTop(productoMetricaRepository.top3PorMetrica("whatsapp"));
    }

    public List<ProductoTopDashboardResponse> obtenerTop3Vistas() {
        return mapProductoTop(productoMetricaRepository.top3PorMetrica("vistas"));
    }

    public List<ProductoTopDashboardResponse> obtenerTop3Abandono() {
        return mapProductoTop(productoMetricaRepository.top3Abandono());
    }

    private ProductoConversionResponse mapProductoConversion(Object[] fila) {
        return new ProductoConversionResponse(
                ((Number) fila[0]).longValue(),
                String.valueOf(fila[1]),
                ((Number) fila[2]).longValue(),
                ((Number) fila[3]).longValue(),
                ((Number) fila[4]).longValue(),
                ((Number) fila[5]).longValue(),
                ((Number) fila[6]).longValue(),
                new BigDecimal(String.valueOf(fila[7]))
        );
    }

    private List<ProductoTopDashboardResponse> mapProductoTop(List<Object[]> filas) {
        return filas.stream()
                .map(fila -> {
                    String modelo = fila[2] != null ? String.valueOf(fila[2]) : null;
                    String imagenUrl = modelo == null || modelo.isBlank()
                            ? "/img-productos/default.png"
                            : "/img-productos/" + modelo.trim() + ".png";

                    return new ProductoTopDashboardResponse(
                            ((Number) fila[0]).longValue(),
                            String.valueOf(fila[1]),
                            modelo,
                            String.valueOf(fila[3]),
                            String.valueOf(fila[4]),
                            ((Number) fila[5]).longValue(),
                            new BigDecimal(String.valueOf(fila[6])),
                            imagenUrl
                    );
                })
                .toList();
    }
}
