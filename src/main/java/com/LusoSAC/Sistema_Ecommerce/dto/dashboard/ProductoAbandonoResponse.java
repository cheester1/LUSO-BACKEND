package com.LusoSAC.Sistema_Ecommerce.dto.dashboard;

public class ProductoAbandonoResponse {

    private Long idProducto;
    private String nombreProducto;
    private Long vistas;
    private Long clicks;
    private Long whatsapp;
    private Long consultas;

    public ProductoAbandonoResponse(
            Long idProducto,
            String nombreProducto,
            Long vistas,
            Long clicks,
            Long whatsapp,
            Long consultas
    ) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.vistas = vistas;
        this.clicks = clicks;
        this.whatsapp = whatsapp;
        this.consultas = consultas;
    }

    public Long getIdProducto() {
        return idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public Long getVistas() {
        return vistas;
    }

    public Long getClicks() {
        return clicks;
    }

    public Long getWhatsapp() {
        return whatsapp;
    }

    public Long getConsultas() {
        return consultas;
    }
}