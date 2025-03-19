package com.kevn.project.ecommerce.e_commerce.controllers;

import java.util.Collections;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.kevn.project.ecommerce.e_commerce.exception.NotExistException;
import com.kevn.project.ecommerce.e_commerce.exception.NotFoundException;
import com.kevn.project.ecommerce.e_commerce.models.ItemProducto;
import com.kevn.project.ecommerce.e_commerce.services.ItemProductoService;

@RestController
@RequestMapping("/api/item-producto")
public class ItemProductoController {

    private final ItemProductoService service;

    public ItemProductoController(ItemProductoService service) {
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
    public ResponseEntity<?> insert(@RequestBody ItemProducto itemProducto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(itemProducto));
    }

    @PostMapping("/insertAll")
    public ResponseEntity<?> insertMultiple(@RequestBody List<ItemProducto> itemProductos) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.saveAll(itemProductos));
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody ItemProducto itemProducto) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(service.save(itemProducto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok("ItemProducto eliminado!");
    }

    /*
     * Este metodo sera de prueba, lo que hara es simular una compra.
     * el id debe ser del ItemProducto
     * Genera un pedido si es nulo.
     */
    @PutMapping("/comprar/{id}")
    public ResponseEntity<?> comprar(@PathVariable Long id) {

        ItemProducto itDb = service.findById(id);

        if (itDb.getId() != null) {
            if (itDb.getProductos().isEmpty()) throw new NotExistException("Los productos no existen");
            service.updateEstadoPedido(itDb);
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
        ItemProducto itDb = service.findById(id);
        if (itDb != null) {

            if (itDb.getProductos().isEmpty()) {
                throw new RuntimeException("Tu lista esta vacia, y esta lista para agregar productos");
            } else {
                service.eliminarTodosLosProductos(itDb.getId());
                return ResponseEntity.ok("Tu lista se ha reestablecido de nuevo, para la siguiente compra");
            }
        } else {
            throw new NotFoundException("El item producto no se ha encontrado para realizar la compra nuevamente");
        }
    }

    /* Lógica de negocio para los productos de ItemProducto */
    @PostMapping("/agregar-producto/{itemProductoId}/{productoId}/{cantidad}")
    public ResponseEntity<?> agregarProducto(
            @PathVariable Long itemProductoId,
            @PathVariable Long productoId,
            @PathVariable int cantidad) {
        service.agregarProducto2(itemProductoId, productoId, cantidad);
        return ResponseEntity.ok("Producto agregado al ItemProducto");
    }

    @PutMapping("/modificar-estado-pedido/{itemProductoId}")
    public ResponseEntity<?> modificarPedido(
            @PathVariable Long itemProductoId) {
        ItemProducto itemProductoDb = service.findById(itemProductoId);
        service.updateEstadoPedido(itemProductoDb);
        return ResponseEntity.ok("El estado del pedido ha sido modificado");
    }

    @DeleteMapping("/eliminar-producto/{itemProductoId}/{productoId}")
    public ResponseEntity<?> eliminarProducto(
            @PathVariable Long itemProductoId,
            @PathVariable Long productoId) {
        service.eliminarProducto(itemProductoId, productoId);
        return ResponseEntity.ok("Producto eliminado del ItemProducto");
    }

    @DeleteMapping("/eliminar-todos-los-productos/{itemProductoId}")
    public ResponseEntity<?> eliminarTodosLosProductos(@PathVariable Long itemProductoId) {
        service.eliminarTodosLosProductos(itemProductoId);
        return ResponseEntity.ok("Todos los productos han sido eliminados del ItemProducto");
    }
}