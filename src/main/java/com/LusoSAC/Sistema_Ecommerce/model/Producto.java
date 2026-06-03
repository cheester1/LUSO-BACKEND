package com.LusoSAC.Sistema_Ecommerce.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Long id;

    @Column(name = "nombre_producto")
    private String nombre;

    @Column(name = "modelo_producto")
    private String modelo;

    @Column(name = "codigo_interno")
    private String codigoInterno;

    @Column(name = "precio_producto")
    private BigDecimal precio;

    @Column(name = "stock_producto")
    private Integer stock;

    @Column(name = "descripcion_producto")
    private String descripcion;

    @Column(name = "id_categoria")
    private Long idCategoria;

    @Column(name = "id_marca")
    private Long idMarca;

    @Column(name = "costo_producto")
    private BigDecimal costo;

    @Column(name = "estado_producto")
    private String estado;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    @Column(name = "es_destacado")
    private Integer esDestacado;

    @Column(name = "ubicacion_fisica")
    private String ubicacionFisica;

    @Column(name = "peso_producto")
    private BigDecimal peso;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_categoria", referencedColumnName = "id_categoria", insertable = false, updatable = false)
    private Categoria categoria;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_marca", referencedColumnName = "id_marca", insertable = false, updatable = false)
    private Marca marca;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public String getCodigoInterno() { return codigoInterno; }
    public void setCodigoInterno(String codigoInterno) { this.codigoInterno = codigoInterno; }

    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Long getIdCategoria() { return idCategoria; }
    public void setIdCategoria(Long idCategoria) { this.idCategoria = idCategoria; }

    public Long getIdMarca() { return idMarca; }
    public void setIdMarca(Long idMarca) { this.idMarca = idMarca; }

    public BigDecimal getCosto() { return costo; }
    public void setCosto(BigDecimal costo) { this.costo = costo; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public LocalDateTime getFechaActualizacion() { return fechaActualizacion; }
    public void setFechaActualizacion(LocalDateTime fechaActualizacion) { this.fechaActualizacion = fechaActualizacion; }

    public Integer getEsDestacado() { return esDestacado; }
    public void setEsDestacado(Integer esDestacado) { this.esDestacado = esDestacado; }

    public String getUbicacionFisica() { return ubicacionFisica; }
    public void setUbicacionFisica(String ubicacionFisica) { this.ubicacionFisica = ubicacionFisica; }

    public BigDecimal getPeso() { return peso; }
    public void setPeso(BigDecimal peso) { this.peso = peso; }

    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }

    public Marca getMarca() { return marca; }
    public void setMarca(Marca marca) { this.marca = marca; }

    public String getNombreCategoria() {
        return categoria != null ? categoria.getNombre() : null;
    }

    public String getNombreMarca() {
        return marca != null ? marca.getNombre() : null;
    }
}
