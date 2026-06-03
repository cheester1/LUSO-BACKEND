package com.LusoSAC.Sistema_Ecommerce.model;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "busquedas_sin_resultado")
public class BusquedaSinResultado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_busqueda")
    private Long id;

    @Column(name = "id_visitante")
    private Long idVisitante;

    @Column(name = "session_id")
    private String sessionId;

    @Column(name = "termino_busqueda")
    private String terminoBusqueda;

    @Column(name = "cantidad_resultados")
    private Integer cantidadResultados;

    @Column(name = "fecha_creacion", insertable = false, updatable = false)
    private Timestamp fechaCreacion;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getIdVisitante() { return idVisitante; }
    public void setIdVisitante(Long idVisitante) { this.idVisitante = idVisitante; }

    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }

    public String getTerminoBusqueda() { return terminoBusqueda; }
    public void setTerminoBusqueda(String terminoBusqueda) { this.terminoBusqueda = terminoBusqueda; }

    public Integer getCantidadResultados() { return cantidadResultados; }
    public void setCantidadResultados(Integer cantidadResultados) { this.cantidadResultados = cantidadResultados; }

    public Timestamp getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(Timestamp fechaCreacion) { this.fechaCreacion = fechaCreacion; }
}