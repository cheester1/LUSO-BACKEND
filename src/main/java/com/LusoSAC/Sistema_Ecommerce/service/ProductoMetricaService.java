package com.LusoSAC.Sistema_Ecommerce.service;

import com.LusoSAC.Sistema_Ecommerce.model.ProductoMetrica;
import com.LusoSAC.Sistema_Ecommerce.repository.ProductoMetricaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ProductoMetricaService {

    private final ProductoMetricaRepository productoMetricaRepository;

    public ProductoMetricaService(ProductoMetricaRepository productoMetricaRepository) {
        this.productoMetricaRepository = productoMetricaRepository;
    }

    public void registrarInteraccionProducto(Long idProducto, String tipo) {
        if (idProducto == null || tipo == null) return;

        ProductoMetrica metrica = productoMetricaRepository.findById(idProducto)
                .orElseGet(() -> crearMetricaInicial(idProducto));

        String tipoNormalizado = tipo.trim().toLowerCase();

        switch (tipoNormalizado) {
            case "vista" -> metrica.setTotalVistas(valorSeguro(metrica.getTotalVistas()) + 1);
            case "click" -> metrica.setTotalClicks(valorSeguro(metrica.getTotalClicks()) + 1);
            case "whatsapp" -> metrica.setTotalWhatsapp(valorSeguro(metrica.getTotalWhatsapp()) + 1);
            case "consulta" -> metrica.setTotalConsultas(valorSeguro(metrica.getTotalConsultas()) + 1);
            case "venta" -> metrica.setTotalVentas(valorSeguro(metrica.getTotalVentas()) + 1);

            /*
             * IMPORTANTE:
             * Por ahora NO existe total_favoritos en ProductoMetrica.
             * Entonces favorito suma al score indirectamente como intención comercial.
             */
            case "favorito" -> metrica.setTotalClicks(valorSeguro(metrica.getTotalClicks()) + 1);

            default -> {
                return;
            }
        }

        metrica.setScoreProducto(calcularScore(metrica));
        productoMetricaRepository.save(metrica);
    }

    public void registrarConsultaProducto(Long idProducto) {
        registrarInteraccionProducto(idProducto, "consulta");
    }

    public void registrarWhatsappProducto(Long idProducto) {
        registrarInteraccionProducto(idProducto, "whatsapp");
    }

    private ProductoMetrica crearMetricaInicial(Long idProducto) {
        ProductoMetrica metrica = new ProductoMetrica();

        metrica.setIdProducto(idProducto);
        metrica.setTotalVistas(0);
        metrica.setTotalClicks(0);
        metrica.setTotalWhatsapp(0);
        metrica.setTotalConsultas(0);
        metrica.setTotalVentas(0);
        metrica.setScoreProducto(BigDecimal.ZERO);

        return metrica;
    }

    private Integer valorSeguro(Integer valor) {
        return valor == null ? 0 : valor;
    }

    private BigDecimal calcularScore(ProductoMetrica metrica) {
        int vistas = valorSeguro(metrica.getTotalVistas());
        int clicks = valorSeguro(metrica.getTotalClicks());
        int whatsapp = valorSeguro(metrica.getTotalWhatsapp());
        int consultas = valorSeguro(metrica.getTotalConsultas());
        int ventas = valorSeguro(metrica.getTotalVentas());

        double score =
                (vistas * 1.0) +
                        (clicks * 3.0) +
                        (whatsapp * 6.0) +
                        (consultas * 8.0) +
                        (ventas * 15.0);

        return BigDecimal.valueOf(score);
    }
}