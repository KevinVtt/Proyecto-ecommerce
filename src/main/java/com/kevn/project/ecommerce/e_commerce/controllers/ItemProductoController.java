package com.kevn.project.ecommerce.e_commerce.controllers;

import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.kevn.project.ecommerce.e_commerce.models.ItemProducto;
import com.kevn.project.ecommerce.e_commerce.services.ItemProductoService;

@RestController
@RequestMapping("/api/item-producto")
public class ItemProductoController {

    @Autowired
    private ItemProductoService service;

    @GetMapping("/findall")
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

    /* Lógica de negocio para los productos de ItemProducto */
    @PostMapping("/agregar-producto/{itemProductoId}/{productoId}/{cantidad}")
    public ResponseEntity<?> agregarProducto(
            @PathVariable Long itemProductoId,
            @PathVariable Long productoId,
            @PathVariable int cantidad) {
        service.agregarProducto(itemProductoId, productoId, cantidad);
        return ResponseEntity.ok("Producto agregado al ItemProducto");
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