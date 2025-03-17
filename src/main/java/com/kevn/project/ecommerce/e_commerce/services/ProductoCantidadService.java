package com.kevn.project.ecommerce.e_commerce.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kevn.project.ecommerce.e_commerce.exception.NotFoundException;
import com.kevn.project.ecommerce.e_commerce.models.ProductoCantidad;
import com.kevn.project.ecommerce.e_commerce.repositories.IProductoCantidad;

@Service
public class ProductoCantidadService implements IService<ProductoCantidad>{

    @Autowired
    private IProductoCantidad repository;

    @Transactional
    @Override
    public void delete(Long id) {
        try {
            Optional<ProductoCantidad> optional = repository.findById(id);
            if(optional.isPresent()){
                repository.deleteById(id);
            }
        } catch (Exception e) {
            throw new RuntimeException(
                    "Error al eliminar el ProductoCantidad con id " + id + ". Mensaje de error: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoCantidad> findAll() {
        try {
            return (List<ProductoCantidad>) repository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar todos los ProductoCantidad: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ProductoCantidad findById(Long id) {
        try {
            return repository.findById(id)
                    .orElseThrow(() -> new NotFoundException("El ProductoCantidad no existe con id: " + id));
        } catch (Exception e) {
            throw new NotFoundException("Error al buscar el ProductoCantidad con id " + id + ". Mensaje de error: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public ProductoCantidad save(ProductoCantidad t) {
        try {
            return repository.save(t);
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar el ProductoCantidad: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public List<ProductoCantidad> saveAll(List<ProductoCantidad> list) {
        try {
            return (List<ProductoCantidad>) repository.saveAll(list);
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar la lista de ProductoCantidad: " + e.getMessage());
        }
    }
}