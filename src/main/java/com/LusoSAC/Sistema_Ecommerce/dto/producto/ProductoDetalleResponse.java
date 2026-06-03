package com.LusoSAC.Sistema_Ecommerce.dto.producto;

import java.math.BigDecimal;
import java.util.List;

public class ProductoDetalleResponse {

    private Long id;
    private String nombre;
    private String modelo;
    private String codigoInterno;
    private BigDecimal precio;
    private Integer stock;
    private String descripcion;

    private Long idCategoria;
    private Long idMarca;

    private String nombreCategoria;
    private String nombreMarca;

    private String estado;
    private List<String> imagenes;

    public ProductoDetalleResponse(
            Long id,
            String nombre,
            String modelo,
            String codigoInterno,
            BigDecimal precio,
            Integer stock,
            String descripcion,
            Long idCategoria,
            Long idMarca,
            String nombreCategoria,
            String nombreMarca,
            String estado,
            List<String> imagenes
    ) {
        this.id = id;
        this.nombre = nombre;
        this.modelo = modelo;
        this.codigoInterno = codigoInterno;
        this.precio = precio;
        this.stock = stock;
        this.descripcion = descripcion;
        this.idCategoria = idCategoria;
        this.idMarca = idMarca;
        this.nombreCategoria = nombreCategoria;
        this.nombreMarca = nombreMarca;
        this.estado = estado;
        this.imagenes = imagenes;
    }

    public Long getId() { return id; }

    public String getNombre() { return nombre; }

    public String getModelo() { return modelo; }

    public String getCodigoInterno() { return codigoInterno; }

    public BigDecimal getPrecio() { return precio; }

    public Integer getStock() { return stock; }

    public String getDescripcion() { return descripcion; }

    public Long getIdCategoria() { return idCategoria; }

    public Long getIdMarca() { return idMarca; }

    public String getNombreCategoria() { return nombreCategoria; }

    public String getNombreMarca() { return nombreMarca; }

    public String getEstado() { return estado; }

    public List<String> getImagenes() { return imagenes; }
}