package com.LusoSAC.Sistema_Ecommerce.dto.producto;

import com.LusoSAC.Sistema_Ecommerce.model.Producto;
import java.math.BigDecimal;

public class ProductoCardResponse {

    private Long id;
    private String nombre;
    private String modelo;
    private BigDecimal precio;
    private Integer stock;
    private String estado;
    private Long idCategoria;
    private Long idMarca;
    private String nombreCategoria;
    private String nombreMarca;

    public ProductoCardResponse(
            Long id,
            String nombre,
            String modelo,
            BigDecimal precio,
            Integer stock,
            String estado,
            Long idCategoria,
            Long idMarca,
            String nombreCategoria,
            String nombreMarca
    ) {
        this.id = id;
        this.nombre = nombre;
        this.modelo = modelo;
        this.precio = precio;
        this.stock = stock;
        this.estado = estado;
        this.idCategoria = idCategoria;
        this.idMarca = idMarca;
        this.nombreCategoria = nombreCategoria;
        this.nombreMarca = nombreMarca;
    }

    public static ProductoCardResponse fromProducto(Producto p) {
        return new ProductoCardResponse(
                p.getId(),
                p.getNombre(),
                p.getModelo(),
                p.getPrecio(),
                p.getStock(),
                p.getEstado(),
                p.getIdCategoria(),
                p.getIdMarca(),
                p.getNombreCategoria(),
                p.getNombreMarca()
        );
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getModelo() { return modelo; }
    public BigDecimal getPrecio() { return precio; }
    public Integer getStock() { return stock; }
    public String getEstado() { return estado; }
    public Long getIdCategoria() { return idCategoria; }
    public Long getIdMarca() { return idMarca; }
    public String getNombreCategoria() { return nombreCategoria; }
    public String getNombreMarca() { return nombreMarca; }
}