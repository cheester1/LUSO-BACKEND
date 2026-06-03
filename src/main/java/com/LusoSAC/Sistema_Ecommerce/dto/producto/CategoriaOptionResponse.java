package com.LusoSAC.Sistema_Ecommerce.dto.producto;

public class CategoriaOptionResponse {

    private Long idCategoria;
    private String nombreCategoria;

    public CategoriaOptionResponse(Long idCategoria, String nombreCategoria) {
        this.idCategoria = idCategoria;
        this.nombreCategoria = nombreCategoria;
    }

    public Long getIdCategoria() {
        return idCategoria;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }
}