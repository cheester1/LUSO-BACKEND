package com.LusoSAC.Sistema_Ecommerce.model;

import jakarta.persistence.*;

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
    private String ip;

    // getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }

    public String getIp() { return ip; }
    public void setIp(String ip) { this.ip = ip; }
}