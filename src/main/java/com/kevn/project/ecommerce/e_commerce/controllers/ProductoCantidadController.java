package com.kevn.project.ecommerce.e_commerce.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.kevn.project.ecommerce.e_commerce.models.ProductoCantidad;
import com.kevn.project.ecommerce.e_commerce.services.ProductoCantidadService;

@RestController
@RequestMapping("/api/producto-cantidad")
public class ProductoCantidadController {

    @Autowired
    private ProductoCantidadService service;

    @GetMapping("/all")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping("/insert")
    public ResponseEntity<?> insert(@RequestBody ProductoCantidad productoCantidad) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(productoCantidad));
    }

    @PostMapping("/insertAll")
    public ResponseEntity<?> insertMultiple(@RequestBody List<ProductoCantidad> productosCantidad) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.saveAll(productosCantidad));
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody ProductoCantidad productoCantidad) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(service.save(productoCantidad));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok("ProductoCantidad eliminado!");
    }
}