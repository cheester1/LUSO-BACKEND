package com.LusoSAC.Sistema_Ecommerce.dto.dashboard;

import java.math.BigDecimal;

public class ProductoConversionResponse {

    private Long idProducto;
    private String nombreProducto;
    private Long vistas;
    private Long clicks;
    private Long whatsapp;
    private Long consultas;
    private Long ventas;
    private BigDecimal tasaConversion;

    public ProductoConversionResponse(
            Long idProducto,
            String nombreProducto,
            Long vistas,
            Long clicks,
            Long whatsapp,
            Long consultas,
            Long ventas,
            BigDecimal tasaConversion
    ) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.vistas = vistas;
        this.clicks = clicks;
        this.whatsapp = whatsapp;
        this.consultas = consultas;
        this.ventas = ventas;
        this.tasaConversion = tasaConversion;
    }

    public Long getIdProducto() { return idProducto; }
    public String getNombreProducto() { return nombreProducto; }
    public Long getVistas() { return vistas; }
    public Long getClicks() { return clicks; }
    public Long getWhatsapp() { return whatsapp; }
    public Long getConsultas() { return consultas; }
    public Long getVentas() { return ventas; }
    public BigDecimal getTasaConversion() { return tasaConversion; }
}