package com.LusoSAC.Sistema_Ecommerce.model;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "consultas_producto")
public class ConsultaProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_consulta")
    private Long id;

    @Column(name = "id_producto")
    private Long idProducto;

    @Column(name = "id_visitante")
    private Long idVisitante;

    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(name = "session_id")
    private String sessionId;

    @Column(name = "nombre_cliente")
    private String nombreCliente;

    @Column(name = "telefono_cliente")
    private String telefonoCliente;

    @Column(name = "mensaje")
    private String mensaje;

    @Column(name = "tipo_consulta")
    private String tipoConsulta;

    @Column(name = "estado_consulta")
    private String estadoConsulta;

    @Column(name = "fecha_creacion", insertable = false, updatable = false)
    private Timestamp fechaCreacion;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getIdProducto() { return idProducto; }
    public void setIdProducto(Long idProducto) { this.idProducto = idProducto; }

    public Long getIdVisitante() { return idVisitante; }
    public void setIdVisitante(Long idVisitante) { this.idVisitante = idVisitante; }

    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }

    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }

    public String getNombreCliente() { return nombreCliente; }
    public void setNombreCliente(String nombreCliente) { this.nombreCliente = nombreCliente; }

    public String getTelefonoCliente() { return telefonoCliente; }
    public void setTelefonoCliente(String telefonoCliente) { this.telefonoCliente = telefonoCliente; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }

    public String getTipoConsulta() { return tipoConsulta; }
    public void setTipoConsulta(String tipoConsulta) { this.tipoConsulta = tipoConsulta; }

    public String getEstadoConsulta() { return estadoConsulta; }
    public void setEstadoConsulta(String estadoConsulta) { this.estadoConsulta = estadoConsulta; }

    public Timestamp getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(Timestamp fechaCreacion) { this.fechaCreacion = fechaCreacion; }
}
