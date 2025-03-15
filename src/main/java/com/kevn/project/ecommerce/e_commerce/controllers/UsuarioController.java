package com.kevn.project.ecommerce.e_commerce.controllers;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kevn.project.ecommerce.e_commerce.models.Usuario;
import com.kevn.project.ecommerce.e_commerce.services.UsuarioService;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService services;

    @GetMapping("/findall")
    public ResponseEntity<?> findAll() {

        return ResponseEntity.ok(services.findAll());

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return ResponseEntity.ok(services.findById(id));
    }

    @PostMapping("/insert")
    public ResponseEntity<?> insert(@RequestBody Usuario usuario) {
        return ResponseEntity.status(HttpStatus.CREATED).body(services.save(usuario));
    }

    
    @PostMapping("/insertAll")
    public ResponseEntity<?> insertMultipleProducts(@RequestBody List<Usuario> users) {
        return ResponseEntity.status(HttpStatus.CREATED).body(services.saveAll(users));
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody Usuario usuario) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(services.save(usuario));

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        services.delete(id);
        return ResponseEntity.ok("Usuario Eliminado!");
    }

}
