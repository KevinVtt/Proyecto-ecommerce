package com.kevn.project.ecommerce.e_commerce.controllers;

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

    @GetMapping("/all")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping("/insert")
    public ResponseEntity<?> insert(@RequestBody ItemProducto productoCantidad) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(productoCantidad));
    }

    @PostMapping("/insertAll")
    public ResponseEntity<?> insertMultiple(@RequestBody List<ItemProducto> productosCantidad) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.saveAll(productosCantidad));
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody ItemProducto productoCantidad) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(service.save(productoCantidad));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.badRequest().body("El producto no se ha eliminado");
    }
}