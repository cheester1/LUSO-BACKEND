package com.LusoSAC.Sistema_Ecommerce.exception;

import java.time.LocalDateTime;

public class ApiErrorResponse {

    private String mensaje;
    private int codigo;
    private String ruta;
    private LocalDateTime fecha;

    public ApiErrorResponse(String mensaje, int codigo, String ruta) {
        this.mensaje = mensaje;
        this.codigo = codigo;
        this.ruta = ruta;
        this.fecha = LocalDateTime.now();
    }

    public String getMensaje() { return mensaje; }
    public int getCodigo() { return codigo; }
    public String getRuta() { return ruta; }
    public LocalDateTime getFecha() { return fecha; }
}