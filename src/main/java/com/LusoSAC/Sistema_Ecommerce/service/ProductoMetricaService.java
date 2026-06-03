package com.LusoSAC.Sistema_Ecommerce.service;

import com.LusoSAC.Sistema_Ecommerce.model.ProductoMetrica;
import com.LusoSAC.Sistema_Ecommerce.model.ProductoMetricaDiaria;
import com.LusoSAC.Sistema_Ecommerce.repository.ProductoMetricaDiariaRepository;
import com.LusoSAC.Sistema_Ecommerce.repository.ProductoMetricaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class ProductoMetricaService {

    private final ProductoMetricaRepository productoMetricaRepository;
    private final ProductoMetricaDiariaRepository productoMetricaDiariaRepository;

    public ProductoMetricaService(
            ProductoMetricaRepository productoMetricaRepository,
            ProductoMetricaDiariaRepository productoMetricaDiariaRepository
    ) {
        this.productoMetricaRepository = productoMetricaRepository;
        this.productoMetricaDiariaRepository = productoMetricaDiariaRepository;
    }

    public void registrarInteraccionProducto(Long idProducto, String tipo) {
        if (idProducto == null || tipo == null || tipo.isBlank()) return;

        String tipoNormalizado = tipo.trim().toLowerCase();

        ProductoMetrica metrica = productoMetricaRepository.findById(idProducto)
                .orElseGet(() -> crearMetricaInicial(idProducto));

        if (!sumarMetricaGlobal(metrica, tipoNormalizado)) {
            return;
        }

        metrica.setScoreProducto(calcularScore(
                metrica.getTotalVistas(),
                metrica.getTotalClicks(),
                metrica.getTotalWhatsapp(),
                metrica.getTotalConsultas(),
                metrica.getTotalFavoritos(),
                metrica.getTotalVentas()
        ));

        productoMetricaRepository.save(metrica);
        registrarMetricaDiaria(idProducto, tipoNormalizado);
    }

    public void registrarConsultaProducto(Long idProducto) {
        registrarInteraccionProducto(idProducto, "consulta");
    }

    public void registrarWhatsappProducto(Long idProducto) {
        registrarInteraccionProducto(idProducto, "whatsapp");
    }

    private boolean sumarMetricaGlobal(ProductoMetrica metrica, String tipo) {
        switch (tipo) {
            case "vista" -> metrica.setTotalVistas(valorSeguro(metrica.getTotalVistas()) + 1);
            case "click" -> metrica.setTotalClicks(valorSeguro(metrica.getTotalClicks()) + 1);
            case "whatsapp" -> metrica.setTotalWhatsapp(valorSeguro(metrica.getTotalWhatsapp()) + 1);
            case "consulta" -> metrica.setTotalConsultas(valorSeguro(metrica.getTotalConsultas()) + 1);
            case "favorito" -> metrica.setTotalFavoritos(valorSeguro(metrica.getTotalFavoritos()) + 1);
            case "venta" -> metrica.setTotalVentas(valorSeguro(metrica.getTotalVentas()) + 1);
            default -> {
                return false;
            }
        }

        return true;
    }

    private void registrarMetricaDiaria(Long idProducto, String tipo) {
        ProductoMetricaDiaria diaria = productoMetricaDiariaRepository
                .findByIdProductoAndFecha(idProducto, LocalDate.now())
                .orElseGet(() -> crearMetricaDiariaInicial(idProducto));

        switch (tipo) {
            case "vista" -> diaria.setTotalVistas(valorSeguro(diaria.getTotalVistas()) + 1);
            case "click" -> diaria.setTotalClicks(valorSeguro(diaria.getTotalClicks()) + 1);
            case "whatsapp" -> diaria.setTotalWhatsapp(valorSeguro(diaria.getTotalWhatsapp()) + 1);
            case "consulta" -> diaria.setTotalConsultas(valorSeguro(diaria.getTotalConsultas()) + 1);
            case "favorito" -> diaria.setTotalFavoritos(valorSeguro(diaria.getTotalFavoritos()) + 1);
            case "venta" -> diaria.setTotalVentas(valorSeguro(diaria.getTotalVentas()) + 1);
            default -> {
                return;
            }
        }

        diaria.setScoreProducto(calcularScore(
                diaria.getTotalVistas(),
                diaria.getTotalClicks(),
                diaria.getTotalWhatsapp(),
                diaria.getTotalConsultas(),
                diaria.getTotalFavoritos(),
                diaria.getTotalVentas()
        ));

        productoMetricaDiariaRepository.save(diaria);
    }

    private ProductoMetrica crearMetricaInicial(Long idProducto) {
        ProductoMetrica metrica = new ProductoMetrica();
        metrica.setIdProducto(idProducto);
        metrica.setTotalVistas(0);
        metrica.setTotalClicks(0);
        metrica.setTotalWhatsapp(0);
        metrica.setTotalConsultas(0);
        metrica.setTotalFavoritos(0);
        metrica.setTotalVentas(0);
        metrica.setScoreProducto(BigDecimal.ZERO);
        return metrica;
    }

    private ProductoMetricaDiaria crearMetricaDiariaInicial(Long idProducto) {
        ProductoMetricaDiaria diaria = new ProductoMetricaDiaria();
        diaria.setIdProducto(idProducto);
        diaria.setFecha(LocalDate.now());
        diaria.setTotalVistas(0);
        diaria.setTotalClicks(0);
        diaria.setTotalWhatsapp(0);
        diaria.setTotalConsultas(0);
        diaria.setTotalFavoritos(0);
        diaria.setTotalVentas(0);
        diaria.setScoreProducto(BigDecimal.ZERO);
        return diaria;
    }

    private Integer valorSeguro(Integer valor) {
        return valor == null ? 0 : valor;
    }

    private BigDecimal calcularScore(
            Integer totalVistas,
            Integer totalClicks,
            Integer totalWhatsapp,
            Integer totalConsultas,
            Integer totalFavoritos,
            Integer totalVentas
    ) {
        double score =
                (valorSeguro(totalVistas) * 1.0) +
                (valorSeguro(totalClicks) * 3.0) +
                (valorSeguro(totalFavoritos) * 5.0) +
                (valorSeguro(totalWhatsapp) * 6.0) +
                (valorSeguro(totalConsultas) * 8.0) +
                (valorSeguro(totalVentas) * 15.0);

        return BigDecimal.valueOf(score);
    }
}
