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

    @Column(name = "session_id")
    private String sessionId;

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

    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
}