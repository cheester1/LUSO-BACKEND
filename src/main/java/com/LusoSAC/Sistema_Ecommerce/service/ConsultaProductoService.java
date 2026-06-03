package com.LusoSAC.Sistema_Ecommerce.service;

import com.LusoSAC.Sistema_Ecommerce.model.ConsultaProducto;
import com.LusoSAC.Sistema_Ecommerce.repository.ConsultaProductoRepository;
import org.springframework.stereotype.Service;

@Service
public class ConsultaProductoService {

    private final ConsultaProductoRepository consultaProductoRepository;
    private final ProductoMetricaService productoMetricaService;

    public ConsultaProductoService(
            ConsultaProductoRepository consultaProductoRepository,
            ProductoMetricaService productoMetricaService
    ) {
        this.consultaProductoRepository = consultaProductoRepository;
        this.productoMetricaService = productoMetricaService;
    }

    public ConsultaProducto registrar(ConsultaProducto consultaProducto) {
        ConsultaProducto guardada = consultaProductoRepository.save(consultaProducto);

        if (consultaProducto.getIdProducto() != null) {
            productoMetricaService.registrarConsultaProducto(
                    consultaProducto.getIdProducto()
            );
        }

        return guardada;
    }
}