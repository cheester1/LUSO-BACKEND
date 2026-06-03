package com.LusoSAC.Sistema_Ecommerce.dto.home;

import com.LusoSAC.Sistema_Ecommerce.dto.producto.ProductoCardResponse;

import java.util.List;

public class HomeResponse {

    private List<ProductoCardResponse> productosDestacados;
    private List<ProductoCardResponse> productosRecomendados;
    private List<ProductoCardResponse> productosPopulares;

    public HomeResponse(
            List<ProductoCardResponse> productosDestacados,
            List<ProductoCardResponse> productosRecomendados,
            List<ProductoCardResponse> productosPopulares
    ) {
        this.productosDestacados = productosDestacados;
        this.productosRecomendados = productosRecomendados;
        this.productosPopulares = productosPopulares;
    }

    public List<ProductoCardResponse> getProductosDestacados() {
        return productosDestacados;
    }

    public List<ProductoCardResponse> getProductosRecomendados() {
        return productosRecomendados;
    }

    public List<ProductoCardResponse> getProductosPopulares() {
        return productosPopulares;
    }
}