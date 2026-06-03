package com.LusoSAC.Sistema_Ecommerce.service;

import com.LusoSAC.Sistema_Ecommerce.model.StockMovimiento;
import com.LusoSAC.Sistema_Ecommerce.repository.StockMovimientoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockMovimientoService {

    private final StockMovimientoRepository stockMovimientoRepository;

    public StockMovimientoService(StockMovimientoRepository stockMovimientoRepository) {
        this.stockMovimientoRepository = stockMovimientoRepository;
    }

    public List<StockMovimiento> listarTodos() {
        return stockMovimientoRepository.findAll();
    }

    public StockMovimiento registrar(StockMovimiento movimiento) {
        return stockMovimientoRepository.save(movimiento);
    }

    public List<StockMovimiento> listarPorProducto(Long idProducto) {
        return stockMovimientoRepository.findByIdProducto(idProducto);
    }
}