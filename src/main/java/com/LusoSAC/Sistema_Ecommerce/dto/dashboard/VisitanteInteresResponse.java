package com.LusoSAC.Sistema_Ecommerce.dto.dashboard;

public class VisitanteInteresResponse {

    private Long idVisitante;
    private String sessionId;
    private String categoriaPrincipal;
    private Long totalInteracciones;

    public VisitanteInteresResponse(
            Long idVisitante,
            String sessionId,
            String categoriaPrincipal,
            Long totalInteracciones
    ) {
        this.idVisitante = idVisitante;
        this.sessionId = sessionId;
        this.categoriaPrincipal = categoriaPrincipal;
        this.totalInteracciones = totalInteracciones;
    }

    public Long getIdVisitante() { return idVisitante; }
    public String getSessionId() { return sessionId; }
    public String getCategoriaPrincipal() { return categoriaPrincipal; }
    public Long getTotalInteracciones() { return totalInteracciones; }
}