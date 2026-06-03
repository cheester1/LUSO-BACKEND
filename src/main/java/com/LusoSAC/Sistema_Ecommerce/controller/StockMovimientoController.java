package com.LusoSAC.Sistema_Ecommerce.controller;

import com.LusoSAC.Sistema_Ecommerce.model.StockMovimiento;
import com.LusoSAC.Sistema_Ecommerce.service.StockMovimientoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stock-movimientos")
public class StockMovimientoController {

    private final StockMovimientoService stockMovimientoService;

    public StockMovimientoController(StockMovimientoService stockMovimientoService) {
        this.stockMovimientoService = stockMovimientoService;
    }

    @GetMapping("/todos")
    public List<StockMovimiento> listarTodos() {
        return stockMovimientoService.listarTodos();
    }

    @GetMapping("/producto/{idProducto}")
    public List<StockMovimiento> listarPorProducto(@PathVariable Long idProducto) {
        return stockMovimientoService.listarPorProducto(idProducto);
    }

    @PostMapping
    public StockMovimiento guardar(@RequestBody StockMovimiento movimiento) {
        return stockMovimientoService.registrar(movimiento);
    }
}