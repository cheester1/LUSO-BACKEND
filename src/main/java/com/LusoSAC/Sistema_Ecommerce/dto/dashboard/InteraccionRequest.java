package com.LusoSAC.Sistema_Ecommerce.dto.dashboard;

public class InteraccionRequest {

    private Long idProducto;
    private Long idServicio;
    private Long idUsuario;
    private Long idVisitante;
    private String sessionId;
    private String tipo;
    private String detalle;
    private String ipAddress;
    private String userAgent;
    private String rol;
    private String rolOrigen;
    private String origenPagina;

    public Long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }

    public Long getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(Long idServicio) {
        this.idServicio = idServicio;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Long getIdVisitante() {
        return idVisitante;
    }

    public void setIdVisitante(Long idVisitante) {
        this.idVisitante = idVisitante;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getRolOrigen() {
        return rolOrigen;
    }

    public void setRolOrigen(String rolOrigen) {
        this.rolOrigen = rolOrigen;
    }

    public String getOrigenPagina() {
        return origenPagina;
    }

    public void setOrigenPagina(String origenPagina) {
        this.origenPagina = origenPagina;
    }
}
