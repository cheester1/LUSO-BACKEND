package com.LusoSAC.Sistema_Ecommerce.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "servicios")
public class Servicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_servicio")
    private Long id;

    @Column(name = "nombre_servicio")
    private String nombre;

    @Column(name = "descripcion_servicio")
    private String descripcion;

    @Column(name = "precio_servicio")
    private BigDecimal precio;

    @Column(name = "categoria_servicio")
    private String categoria;

    @Column(name = "estado_servicio")
    private String estadoServicio;

    @Column(name = "proporcion_servicio")
    private String proporcion;

    @Column(name = "img_servicio_url")
    private String imagen;

    @Column(name = "estado")
    private Integer estado;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getEstadoServicio() { return estadoServicio; }
    public void setEstadoServicio(String estadoServicio) { this.estadoServicio = estadoServicio; }

    public String getProporcion() { return proporcion; }
    public void setProporcion(String proporcion) { this.proporcion = proporcion; }

    public String getImagen() { return imagen; }
    public void setImagen(String imagen) { this.imagen = imagen; }

    public Integer getEstado() { return estado; }
    public void setEstado(Integer estado) { this.estado = estado; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
}