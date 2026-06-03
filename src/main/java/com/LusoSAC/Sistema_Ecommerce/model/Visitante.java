package com.LusoSAC.Sistema_Ecommerce.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "visitantes")
public class Visitante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_visitante")
    private Long id;

    @Column(name = "session_id")
    private String sessionId;

    @Column(name = "ip")
    private String ipAddress;

    @Column(name = "user_agent")
    private String userAgent;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaPrimeraVisita;

    @Column(name = "ultima_visita")
    private LocalDateTime fechaUltimaVisita;

    @Transient
    private Integer totalVisitas;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }

    public String getIpAddress() { return ipAddress; }
    public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }

    public String getUserAgent() { return userAgent; }
    public void setUserAgent(String userAgent) { this.userAgent = userAgent; }

    public LocalDateTime getFechaPrimeraVisita() { return fechaPrimeraVisita; }
    public void setFechaPrimeraVisita(LocalDateTime fechaPrimeraVisita) { this.fechaPrimeraVisita = fechaPrimeraVisita; }

    public LocalDateTime getFechaUltimaVisita() { return fechaUltimaVisita; }
    public void setFechaUltimaVisita(LocalDateTime fechaUltimaVisita) { this.fechaUltimaVisita = fechaUltimaVisita; }

    public Integer getTotalVisitas() { return totalVisitas; }
    public void setTotalVisitas(Integer totalVisitas) { this.totalVisitas = totalVisitas; }
}