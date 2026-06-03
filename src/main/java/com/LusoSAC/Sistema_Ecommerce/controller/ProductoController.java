package com.LusoSAC.Sistema_Ecommerce.controller;

import com.LusoSAC.Sistema_Ecommerce.dto.producto.ProductoCardResponse;
import com.LusoSAC.Sistema_Ecommerce.dto.producto.ProductoDetalleResponse;
import com.LusoSAC.Sistema_Ecommerce.dto.producto.ProductoFiltroRequest;
import com.LusoSAC.Sistema_Ecommerce.dto.producto.ProductoFiltroResponse;
import com.LusoSAC.Sistema_Ecommerce.model.BusquedaProducto;
import com.LusoSAC.Sistema_Ecommerce.model.BusquedaSinResultado;
import com.LusoSAC.Sistema_Ecommerce.model.Visitante;
import com.LusoSAC.Sistema_Ecommerce.repository.BusquedaProductoRepository;
import com.LusoSAC.Sistema_Ecommerce.repository.BusquedaSinResultadoRepository;
import com.LusoSAC.Sistema_Ecommerce.repository.ProductoRepository;
import com.LusoSAC.Sistema_Ecommerce.repository.VisitanteRepository;
import com.LusoSAC.Sistema_Ecommerce.service.ProductoService;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;
    private final ProductoRepository productoRepository;
    private final BusquedaSinResultadoRepository busquedaSinResultadoRepository;
    private final BusquedaProductoRepository busquedaProductoRepository;
    private final VisitanteRepository visitanteRepository;

    public ProductoController(
            ProductoService productoService,
            ProductoRepository productoRepository,
            BusquedaSinResultadoRepository busquedaSinResultadoRepository,
            BusquedaProductoRepository busquedaProductoRepository,
            VisitanteRepository visitanteRepository
    ) {
        this.productoService = productoService;
        this.productoRepository = productoRepository;
        this.busquedaSinResultadoRepository = busquedaSinResultadoRepository;
        this.busquedaProductoRepository = busquedaProductoRepository;
        this.visitanteRepository = visitanteRepository;
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
            @RequestParam(required = false) Long idUsuario,
            @RequestParam(required = false) String sessionId,
            @RequestParam(required = false) String rol,
            @RequestParam(required = false) String origenPagina,
            @RequestParam(defaultValue = "true") Boolean registrar
    ) {
        if (q == null || q.trim().isEmpty()) {
            throw new IllegalArgumentException("El término de búsqueda es obligatorio");
        }

        String termino = q.trim();
        List<ProductoCardResponse> resultados = productoRepository.buscarCards(termino);

        if (Boolean.TRUE.equals(registrar) && !esAdministrador(rol)) {
            registrarBusqueda(termino, resultados.size(), idVisitante, idUsuario, sessionId, rol, origenPagina);
        }

        return ResponseEntity.ok(resultados);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoDetalleResponse> obtener(@PathVariable Long id) {
        return productoRepository.findById(id)
                .map(producto -> {
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
                            List.of()
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
            Long idUsuario,
            String sessionId,
            String rol,
            String origenPagina
    ) {
        boolean usuarioLogueado = idUsuario != null && idUsuario > 0;
        Visitante visitante = usuarioLogueado ? null : obtenerOCrearVisitante(idVisitante, sessionId);

        BusquedaProducto busquedaProducto = new BusquedaProducto();
        busquedaProducto.setTerminoBusqueda(termino);
        busquedaProducto.setCantidadResultados(cantidadResultados);
        busquedaProducto.setIdUsuario(usuarioLogueado ? idUsuario : null);
        busquedaProducto.setIdVisitante(visitante != null ? visitante.getId() : null);
        busquedaProducto.setSessionId(usuarioLogueado ? null : limpiar(sessionId));
        busquedaProducto.setRolOrigen(usuarioLogueado ? "USUARIO" : "VISITANTE");
        busquedaProducto.setOrigenPagina(normalizarOrigenPagina(origenPagina));
        busquedaProductoRepository.save(busquedaProducto);

        if (cantidadResultados == 0) {
            BusquedaSinResultado busquedaSinResultado = new BusquedaSinResultado();
            busquedaSinResultado.setTerminoBusqueda(termino);
            busquedaSinResultado.setCantidadResultados(0);
            busquedaSinResultado.setIdUsuario(usuarioLogueado ? idUsuario : null);
            busquedaSinResultado.setIdVisitante(visitante != null ? visitante.getId() : null);
            busquedaSinResultado.setSessionId(usuarioLogueado ? null : limpiar(sessionId));
            busquedaSinResultado.setRolOrigen(usuarioLogueado ? "USUARIO" : "VISITANTE");
            busquedaSinResultado.setOrigenPagina(normalizarOrigenPagina(origenPagina));
            busquedaSinResultadoRepository.save(busquedaSinResultado);
        }
    }

    private Visitante obtenerOCrearVisitante(Long idVisitante, String sessionId) {
        if (idVisitante != null && idVisitante > 0) {
            return visitanteRepository.findById(idVisitante).orElse(null);
        }

        String cleanSessionId = limpiar(sessionId);

        if (cleanSessionId == null) {
            return null;
        }

        return visitanteRepository.findBySessionId(cleanSessionId)
                .map(visitante -> {
                    visitante.setFechaUltimaVisita(LocalDateTime.now());
                    return visitanteRepository.save(visitante);
                })
                .orElseGet(() -> {
                    Visitante nuevo = new Visitante();
                    nuevo.setSessionId(cleanSessionId);
                    nuevo.setFechaPrimeraVisita(LocalDateTime.now());
                    nuevo.setFechaUltimaVisita(LocalDateTime.now());
                    return visitanteRepository.save(nuevo);
                });
    }

    private String normalizarOrigenPagina(String origenPagina) {
        if (origenPagina == null || origenPagina.isBlank()) {
            return "PRODUCTOS";
        }

        return origenPagina.trim().toUpperCase();
    }

    private String limpiar(String texto) {
        if (texto == null || texto.isBlank()) {
            return null;
        }

        return texto.trim();
    }
}
