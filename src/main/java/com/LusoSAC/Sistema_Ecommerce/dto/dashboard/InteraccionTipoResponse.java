package com.LusoSAC.Sistema_Ecommerce.dto.dashboard;

public class InteraccionTipoResponse {

    private String tipo;
    private Long total;

    public InteraccionTipoResponse(String tipo, Long total) {
        this.tipo = tipo;
        this.total = total;
    }

    public String getTipo() { return tipo; }
    public Long getTotal() { return total; }
}