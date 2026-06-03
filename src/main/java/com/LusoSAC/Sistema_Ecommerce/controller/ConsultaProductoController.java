package com.LusoSAC.Sistema_Ecommerce.controller;

import com.LusoSAC.Sistema_Ecommerce.model.ConsultaProducto;
import com.LusoSAC.Sistema_Ecommerce.repository.ConsultaProductoRepository;
import com.LusoSAC.Sistema_Ecommerce.service.ConsultaProductoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/consultas-producto")
public class ConsultaProductoController {

    private final ConsultaProductoRepository consultaProductoRepository;
    private final ConsultaProductoService consultaProductoService;

    public ConsultaProductoController(
            ConsultaProductoRepository consultaProductoRepository,
            ConsultaProductoService consultaProductoService
    ) {
        this.consultaProductoRepository = consultaProductoRepository;
        this.consultaProductoService = consultaProductoService;
    }

    @GetMapping("/todos")
    public List<ConsultaProducto> listarTodos() {
        return consultaProductoRepository.findAll();
    }

    @PostMapping
    public ConsultaProducto guardar(@RequestBody ConsultaProducto consulta) {
        return consultaProductoService.registrar(consulta);
    }
}