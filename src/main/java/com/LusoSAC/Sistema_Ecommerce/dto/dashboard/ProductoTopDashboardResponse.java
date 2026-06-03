package com.LusoSAC.Sistema_Ecommerce.dto.dashboard;

import java.math.BigDecimal;

public class ProductoTopDashboardResponse {

    private Long idProducto;
    private String nombre;
    private String modelo;
    private String marca;
    private String categoria;
    private Long total;
    private BigDecimal porcentaje;
    private String imagenUrl;

    public ProductoTopDashboardResponse(
            Long idProducto,
            String nombre,
            String modelo,
            String marca,
            String categoria,
            Long total,
            BigDecimal porcentaje,
            String imagenUrl
    ) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.modelo = modelo;
        this.marca = marca;
        this.categoria = categoria;
        this.total = total;
        this.porcentaje = porcentaje;
        this.imagenUrl = imagenUrl;
    }

    public Long getIdProducto() { return idProducto; }
    public String getNombre() { return nombre; }
    public String getModelo() { return modelo; }
    public String getMarca() { return marca; }
    public String getCategoria() { return categoria; }
    public Long getTotal() { return total; }
    public BigDecimal getPorcentaje() { return porcentaje; }
    public String getImagenUrl() { return imagenUrl; }
}
