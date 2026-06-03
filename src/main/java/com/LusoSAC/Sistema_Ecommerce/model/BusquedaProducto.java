package com.LusoSAC.Sistema_Ecommerce.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "busquedas_producto")
public class BusquedaProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_busqueda_producto")
    private Long id;

    @Column(name = "termino_busqueda")
    private String terminoBusqueda;

    @Column(name = "cantidad_resultados")
    private Integer cantidadResultados;

    @Column(name = "id_visitante")
    private Long idVisitante;

    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(name = "session_id")
    private String sessionId;

    @Column(name = "origen_pagina")
    private String origenPagina;

    @Column(name = "rol_origen")
    private String rolOrigen;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    public BusquedaProducto() {
        this.fechaCreacion = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTerminoBusqueda() { return terminoBusqueda; }
    public void setTerminoBusqueda(String terminoBusqueda) { this.terminoBusqueda = terminoBusqueda; }

    public Integer getCantidadResultados() { return cantidadResultados; }
    public void setCantidadResultados(Integer cantidadResultados) { this.cantidadResultados = cantidadResultados; }

    public Long getIdVisitante() { return idVisitante; }
    public void setIdVisitante(Long idVisitante) { this.idVisitante = idVisitante; }

    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }

    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }

    public String getOrigenPagina() { return origenPagina; }
    public void setOrigenPagina(String origenPagina) { this.origenPagina = origenPagina; }

    public String getRolOrigen() { return rolOrigen; }
    public void setRolOrigen(String rolOrigen) { this.rolOrigen = rolOrigen; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
}
