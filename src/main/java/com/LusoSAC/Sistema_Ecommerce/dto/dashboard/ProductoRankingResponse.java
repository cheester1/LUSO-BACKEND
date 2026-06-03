package com.LusoSAC.Sistema_Ecommerce.dto.dashboard;

import java.math.BigDecimal;

public class ProductoRankingResponse {

    private Long idProducto;
    private String nombreProducto;
    private String modeloProducto;
    private BigDecimal precioProducto;
    private Long total;

    public ProductoRankingResponse(
            Long idProducto,
            String nombreProducto,
            String modeloProducto,
            BigDecimal precioProducto,
            Long total
    ) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.modeloProducto = modeloProducto;
        this.precioProducto = precioProducto;
        this.total = total;
    }

    public Long getIdProducto() { return idProducto; }
    public String getNombreProducto() { return nombreProducto; }
    public String getModeloProducto() { return modeloProducto; }
    public BigDecimal getPrecioProducto() { return precioProducto; }
    public Long getTotal() { return total; }
}