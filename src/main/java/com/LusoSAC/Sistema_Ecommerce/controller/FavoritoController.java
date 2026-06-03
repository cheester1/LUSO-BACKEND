package com.LusoSAC.Sistema_Ecommerce.controller;

import com.LusoSAC.Sistema_Ecommerce.dto.producto.ProductoCardResponse;
import com.LusoSAC.Sistema_Ecommerce.model.Favorito;
import com.LusoSAC.Sistema_Ecommerce.repository.FavoritoRepository;
import com.LusoSAC.Sistema_Ecommerce.repository.ProductoRepository;
import com.LusoSAC.Sistema_Ecommerce.service.FavoritoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favoritos")
public class FavoritoController {

    private final FavoritoRepository favoritoRepository;
    private final ProductoRepository productoRepository;
    private final FavoritoService favoritoService;

    public FavoritoController(
            FavoritoRepository favoritoRepository,
            ProductoRepository productoRepository,
            FavoritoService favoritoService
    ) {
        this.favoritoRepository = favoritoRepository;
        this.productoRepository = productoRepository;
        this.favoritoService = favoritoService;
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<Favorito>> listarFavoritosPorUsuario(@PathVariable Long idUsuario) {
        if (idUsuario == null || idUsuario <= 0) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(favoritoService.listarPorUsuario(idUsuario));
    }

    @GetMapping("/usuario/{idUsuario}/productos")
    public ResponseEntity<List<ProductoCardResponse>> productosFavoritosPorUsuario(@PathVariable Long idUsuario) {
        if (idUsuario == null || idUsuario <= 0) {
            return ResponseEntity.badRequest().build();
        }

        List<Long> ids = favoritoRepository.findByIdUsuario(idUsuario)
                .stream()
                .map(Favorito::getIdProducto)
                .filter(id -> id != null)
                .distinct()
                .toList();

        if (ids.isEmpty()) {
            return ResponseEntity.ok(List.of());
        }

        return ResponseEntity.ok(productoRepository.cardsPorIds(ids));
    }

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody Favorito favorito) {
        if (favorito.getIdProducto() == null || favorito.getIdProducto() <= 0) {
            return ResponseEntity.badRequest().body("El producto es obligatorio");
        }

        if (favorito.getIdUsuario() == null || favorito.getIdUsuario() <= 0) {
            return ResponseEntity.badRequest().body("El usuario es obligatorio");
        }

        Favorito guardado = favoritoService.agregar(
                favorito.getIdProducto(),
                favorito.getIdUsuario()
        );

        return ResponseEntity.ok(guardado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest().body("El favorito es obligatorio");
        }

        favoritoService.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}