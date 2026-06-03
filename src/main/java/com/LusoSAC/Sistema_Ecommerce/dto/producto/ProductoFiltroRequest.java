package com.LusoSAC.Sistema_Ecommerce.dto.producto;

import java.math.BigDecimal;

public class ProductoFiltroRequest {

    private String q;
    private Long idCategoria;
    private Long idMarca;
    private String modelo;
    private BigDecimal precioMin;
    private BigDecimal precioMax;
    private Integer page = 0;
    private Integer size = 12;
    private String sort = "recientes";

    public String getQ() { return q; }
    public void setQ(String q) { this.q = q; }

    public Long getIdCategoria() { return idCategoria; }
    public void setIdCategoria(Long idCategoria) { this.idCategoria = idCategoria; }

    public Long getIdMarca() { return idMarca; }
    public void setIdMarca(Long idMarca) { this.idMarca = idMarca; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public BigDecimal getPrecioMin() { return precioMin; }
    public void setPrecioMin(BigDecimal precioMin) { this.precioMin = precioMin; }

    public BigDecimal getPrecioMax() { return precioMax; }
    public void setPrecioMax(BigDecimal precioMax) { this.precioMax = precioMax; }

    public Integer getPage() { return page; }
    public void setPage(Integer page) { this.page = page; }

    public Integer getSize() { return size; }
    public void setSize(Integer size) { this.size = size; }

    public String getSort() { return sort; }
    public void setSort(String sort) { this.sort = sort; }
}