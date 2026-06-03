package com.LusoSAC.Sistema_Ecommerce.service;

import com.LusoSAC.Sistema_Ecommerce.model.Favorito;
import com.LusoSAC.Sistema_Ecommerce.repository.FavoritoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FavoritoService {

    private final FavoritoRepository favoritoRepository;
    private final ProductoMetricaService productoMetricaService;

    public FavoritoService(
            FavoritoRepository favoritoRepository,
            ProductoMetricaService productoMetricaService
    ) {
        this.favoritoRepository = favoritoRepository;
        this.productoMetricaService = productoMetricaService;
    }

    public List<Favorito> listarPorUsuario(Long idUsuario) {
        return favoritoRepository.findByIdUsuario(idUsuario);
    }

    public Favorito agregar(Long idProducto, Long idUsuario) {
        return favoritoRepository
                .findByIdProductoAndIdUsuario(idProducto, idUsuario)
                .orElseGet(() -> crearFavorito(idProducto, idUsuario));
    }

    public void eliminar(Long idFavorito) {
        if (!favoritoRepository.existsById(idFavorito)) {
            throw new RuntimeException("No se encontró el favorito");
        }

        favoritoRepository.deleteById(idFavorito);
    }

    private Favorito crearFavorito(Long idProducto, Long idUsuario) {
        Favorito favorito = new Favorito();
        favorito.setIdProducto(idProducto);
        favorito.setIdUsuario(idUsuario);
        favorito.setFechaCreacion(LocalDateTime.now());

        Favorito guardado = favoritoRepository.save(favorito);

        productoMetricaService.registrarInteraccionProducto(idProducto, "favorito");

        return guardado;
    }
}