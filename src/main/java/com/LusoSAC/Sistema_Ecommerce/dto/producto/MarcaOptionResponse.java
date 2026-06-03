package com.LusoSAC.Sistema_Ecommerce.dto.producto;

public class MarcaOptionResponse {

    private Long idMarca;
    private String nombreMarca;

    public MarcaOptionResponse(Long idMarca, String nombreMarca) {
        this.idMarca = idMarca;
        this.nombreMarca = nombreMarca;
    }

    public Long getIdMarca() {
        return idMarca;
    }

    public String getNombreMarca() {
        return nombreMarca;
    }
}