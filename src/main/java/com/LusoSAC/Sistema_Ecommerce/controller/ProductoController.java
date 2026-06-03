package com.LusoSAC.Sistema_Ecommerce.controller;

import com.LusoSAC.Sistema_Ecommerce.dto.producto.ProductoCardResponse;
import com.LusoSAC.Sistema_Ecommerce.dto.producto.ProductoDetalleResponse;
import com.LusoSAC.Sistema_Ecommerce.dto.producto.ProductoFiltroRequest;
import com.LusoSAC.Sistema_Ecommerce.dto.producto.ProductoFiltroResponse;
import com.LusoSAC.Sistema_Ecommerce.model.BusquedaProducto;
import com.LusoSAC.Sistema_Ecommerce.model.BusquedaSinResultado;
import com.LusoSAC.Sistema_Ecommerce.model.ProductoImagen;
import com.LusoSAC.Sistema_Ecommerce.repository.BusquedaProductoRepository;
import com.LusoSAC.Sistema_Ecommerce.repository.BusquedaSinResultadoRepository;
import com.LusoSAC.Sistema_Ecommerce.repository.ProductoImagenRepository;
import com.LusoSAC.Sistema_Ecommerce.repository.ProductoRepository;
import com.LusoSAC.Sistema_Ecommerce.service.ProductoService;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;
    private final ProductoImagenRepository productoImagenRepository;
    private final ProductoRepository productoRepository;
    private final BusquedaSinResultadoRepository busquedaSinResultadoRepository;
    private final BusquedaProductoRepository busquedaProductoRepository;

    public ProductoController(
            ProductoService productoService,
            ProductoImagenRepository productoImagenRepository,
            ProductoRepository productoRepository,
            BusquedaSinResultadoRepository busquedaSinResultadoRepository,
            BusquedaProductoRepository busquedaProductoRepository
    ) {
        this.productoService = productoService;
        this.productoImagenRepository = productoImagenRepository;
        this.productoRepository = productoRepository;
        this.busquedaSinResultadoRepository = busquedaSinResultadoRepository;
        this.busquedaProductoRepository = busquedaProductoRepository;
    }

    @GetMapping("/random")
    public List<ProductoCardResponse> random() {
        return productoRepository.listarCards();
    }

    @GetMapping
    public Page<ProductoCardResponse> listarPaginado(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(
                Math.max(page, 0),
                Math.min(Math.max(size, 1), 100),
                Sort.by("id").descending()
        );

        return productoRepository.listarCardsPaginado(pageable);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<ProductoCardResponse>> buscar(
            @RequestParam String q,
            @RequestParam(required = false) Long idVisitante,
            @RequestParam(required = false) String sessionId,
            @RequestParam(required = false) String rol,
            @RequestParam(defaultValue = "true") Boolean registrar
    ) {
        if (q == null || q.trim().isEmpty()) {
            throw new IllegalArgumentException("El término de búsqueda es obligatorio");
        }

        String termino = q.trim();
        List<ProductoCardResponse> resultados = productoRepository.buscarCards(termino);

        if (Boolean.TRUE.equals(registrar) && !esAdministrador(rol)) {
            registrarBusqueda(termino, resultados.size(), idVisitante, sessionId);
        }

        return ResponseEntity.ok(resultados);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoDetalleResponse> obtener(@PathVariable Long id) {
        return productoRepository.findById(id)
                .map(producto -> {
                    List<String> imagenes = productoImagenRepository.findByProductoId(id)
                            .stream()
                            .limit(3)
                            .map(ProductoImagen::getUrl)
                            .collect(Collectors.toList());

                    ProductoDetalleResponse response = new ProductoDetalleResponse(
                            producto.getId(),
                            producto.getNombre(),
                            producto.getModelo(),
                            producto.getCodigoInterno(),
                            producto.getPrecio(),
                            producto.getStock(),
                            producto.getDescripcion(),
                            producto.getIdCategoria(),
                            producto.getIdMarca(),
                            producto.getCategoria() != null ? producto.getCategoria().getNombre() : null,
                            producto.getMarca() != null ? producto.getMarca().getNombre() : null,
                            producto.getEstado(),
                            imagenes
                    );

                    return ResponseEntity.ok(response);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cards")
    public List<ProductoCardResponse> listarCards() {
        return productoRepository.listarCards();
    }

    @GetMapping("/filtrar")
    public Page<ProductoCardResponse> filtrar(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) Long idCategoria,
            @RequestParam(required = false) Long idMarca,
            @RequestParam(required = false) String modelo,
            @RequestParam(required = false) BigDecimal precioMin,
            @RequestParam(required = false) BigDecimal precioMax,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "20") Integer size
    ) {
        Pageable pageable = PageRequest.of(
                Math.max(page, 0),
                Math.min(Math.max(size, 1), 50),
                Sort.by("id").descending()
        );

        return productoRepository.filtrarProductosCards(
                q,
                idCategoria,
                idMarca,
                modelo,
                precioMin,
                precioMax,
                pageable
        );
    }

    @GetMapping("/filtrar-page")
    public ProductoFiltroResponse filtrarPage(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) Long idCategoria,
            @RequestParam(required = false) Long idMarca,
            @RequestParam(required = false) String modelo,
            @RequestParam(required = false) BigDecimal precioMin,
            @RequestParam(required = false) BigDecimal precioMax,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "12") Integer size,
            @RequestParam(defaultValue = "recientes") String sort
    ) {
        ProductoFiltroRequest request = new ProductoFiltroRequest();
        request.setQ(q);
        request.setIdCategoria(idCategoria);
        request.setIdMarca(idMarca);
        request.setModelo(modelo);
        request.setPrecioMin(precioMin);
        request.setPrecioMax(precioMax);
        request.setPage(page);
        request.setSize(size);
        request.setSort(sort);

        return productoService.filtrarProductos(request);
    }

    private boolean esAdministrador(String rol) {
        if (rol == null || rol.isBlank()) {
            return false;
        }

        String normalizado = rol.trim().toUpperCase();

        return normalizado.equals("ADMINISTRADOR")
                || normalizado.equals("ROLE_ADMINISTRADOR")
                || normalizado.equals("ADMIN");
    }

    private void registrarBusqueda(
            String termino,
            int cantidadResultados,
            Long idVisitante,
            String sessionId
    ) {
        BusquedaProducto busquedaProducto = new BusquedaProducto();
        busquedaProducto.setTerminoBusqueda(termino);
        busquedaProducto.setCantidadResultados(cantidadResultados);
        busquedaProducto.setIdVisitante(idVisitante);
        busquedaProducto.setSessionId(sessionId);
        busquedaProductoRepository.save(busquedaProducto);

        if (cantidadResultados == 0) {
            BusquedaSinResultado busquedaSinResultado = new BusquedaSinResultado();
            busquedaSinResultado.setTerminoBusqueda(termino);
            busquedaSinResultado.setCantidadResultados(0);
            busquedaSinResultado.setIdVisitante(idVisitante);
            busquedaSinResultado.setSessionId(sessionId);
            busquedaSinResultadoRepository.save(busquedaSinResultado);
        }
    }
}