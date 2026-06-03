package com.LusoSAC.Sistema_Ecommerce.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "producto_metricas_diarias")
public class ProductoMetricaDiaria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_metrica")
    private Long id;

    @Column(name = "id_producto")
    private Long idProducto;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(name = "total_vistas")
    private Integer totalVistas;

    @Column(name = "total_clicks")
    private Integer totalClicks;

    @Column(name = "total_whatsapp")
    private Integer totalWhatsapp;

    @Column(name = "total_consultas")
    private Integer totalConsultas;

    @Column(name = "total_favoritos")
    private Integer totalFavoritos;

    @Column(name = "total_ventas")
    private Integer totalVentas;

    @Column(name = "score_producto")
    private BigDecimal scoreProducto;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getIdProducto() { return idProducto; }
    public void setIdProducto(Long idProducto) { this.idProducto = idProducto; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public Integer getTotalVistas() { return totalVistas; }
    public void setTotalVistas(Integer totalVistas) { this.totalVistas = totalVistas; }

    public Integer getTotalClicks() { return totalClicks; }
    public void setTotalClicks(Integer totalClicks) { this.totalClicks = totalClicks; }

    public Integer getTotalWhatsapp() { return totalWhatsapp; }
    public void setTotalWhatsapp(Integer totalWhatsapp) { this.totalWhatsapp = totalWhatsapp; }

    public Integer getTotalConsultas() { return totalConsultas; }
    public void setTotalConsultas(Integer totalConsultas) { this.totalConsultas = totalConsultas; }

    public Integer getTotalFavoritos() { return totalFavoritos; }
    public void setTotalFavoritos(Integer totalFavoritos) { this.totalFavoritos = totalFavoritos; }

    public Integer getTotalVentas() { return totalVentas; }
    public void setTotalVentas(Integer totalVentas) { this.totalVentas = totalVentas; }

    public BigDecimal getScoreProducto() { return scoreProducto; }
    public void setScoreProducto(BigDecimal scoreProducto) { this.scoreProducto = scoreProducto; }
}
