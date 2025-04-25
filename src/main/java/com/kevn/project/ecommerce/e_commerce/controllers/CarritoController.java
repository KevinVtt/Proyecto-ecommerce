package com.kevn.project.ecommerce.e_commerce.controllers;

import java.util.Collections;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.kevn.project.ecommerce.e_commerce.exception.NotFoundException;
import com.kevn.project.ecommerce.e_commerce.models.Carrito;
import com.kevn.project.ecommerce.e_commerce.services.CarritoService;

@RestController
@RequestMapping("/api/carrito")
public class CarritoController {

    private final CarritoService service;

    public CarritoController(CarritoService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public ResponseEntity<?> findAll() {
        if (service.findAll().isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("Error", "La lista está vacía"));
        }
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping("/insert")
    public ResponseEntity<?> insert(@RequestBody Carrito carrito) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(carrito));
    }

    @PostMapping("/insertAll")
    public ResponseEntity<?> insertMultiple(@RequestBody List<Carrito> carritos) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.saveAll(carritos));
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody Carrito carrito) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(service.save(carrito));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok("Carrito eliminado!");
    }

    /*
     * Este metodo sera de prueba, lo que hara es simular una compra.
     * el id debe ser del ItemProducto
     * Genera un pedido si es nulo.
     */
    @PutMapping("/comprar/{id}")
    public ResponseEntity<?> comprar(@PathVariable Long id) {

        Carrito carritoDb = service.findById(id);

        if (carritoDb.getId() != null) {
            if (!service.verificarSiExistenProductos(carritoDb)) throw new NotFoundException("No existen los productos en tu cuenta");
            service.updateEstadoPedido(carritoDb);
            return ResponseEntity.ok("La compra ha sido completada!");
        }
        
        throw new NotFoundException("El objeto que estas buscando no existe en la base de datos");
        
    }
    /*
     * Realizar otra compra !
     * Este metodo es de forma de prueba para verificar si funciona, la idea ahora
     * es que si quiero hacer otra compra los productos se eliminen.
     * Elimina la lista de productos y setea en nulo el pedido
     */

    @PutMapping("/otra-compra/{id}")
    public ResponseEntity<?> comprarDenuevo(@PathVariable Long id) {
        Carrito carritoDb = service.findById(id);
        if (carritoDb != null) {

            if (!service.verificarSiExistenProductos(carritoDb)) {
                throw new RuntimeException("Tu lista esta vacia, y esta lista para agregar productos");
            } else {
                service.eliminarTodosLosProductos(carritoDb.getId());
                return ResponseEntity.ok("Tu lista se ha reestablecido de nuevo, para la siguiente compra");
            }
        } else {
            throw new NotFoundException("El item producto no se ha encontrado para realizar la compra nuevamente");
        }
    }

    /* Lógica de negocio para los productos de ItemProducto */
    @PostMapping("/agregar-producto/{carritoId}/{productoId}/{cantidad}")
    public ResponseEntity<?> agregarProducto(
            @PathVariable Long carritoId,
            @PathVariable Long productoId,
            @PathVariable int cantidad) {
        service.agregarProducto(carritoId, productoId, cantidad);
        return ResponseEntity.ok("Producto agregado al ItemProducto");
    }

    @PutMapping("/modificar-estado-pedido/{carritoId}")
    public ResponseEntity<?> modificarPedido(
            @PathVariable Long carritoId) {
        Carrito carritoDb = service.findById(carritoId);
        service.updateEstadoPedido(carritoDb);
        return ResponseEntity.ok("El estado del pedido ha sido modificado");
    }

    @DeleteMapping("/eliminar-producto/{carritoId}/{productoId}")
    public ResponseEntity<?> eliminarProducto(
            @PathVariable Long carritoId,
            @PathVariable Long productoId) {
        service.eliminarProducto(carritoId, productoId);
        return ResponseEntity.ok("Producto eliminado del ItemProducto");
    }

    @DeleteMapping("/eliminar-todos-los-productos/{carritoId}")
    public ResponseEntity<?> eliminarTodosLosProductos(@PathVariable Long carritoId) {
        service.eliminarTodosLosProductos(carritoId);
        return ResponseEntity.ok("Todos los productos han sido eliminados del carrito");
    }
}