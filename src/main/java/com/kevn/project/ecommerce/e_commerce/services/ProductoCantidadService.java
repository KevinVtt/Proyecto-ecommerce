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

    private final IProductoCantidad repository;

    public ProductoCantidadService(IProductoCantidad repository) {
        this.repository = repository;
    }

    @Transactional
    @Override
    public void delete(Long id) {
            Optional<ProductoCantidad> optional = repository.findById(id);
            if(optional.isPresent()){
                repository.deleteById(id);
            }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoCantidad> findAll() {
            return  repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public ProductoCantidad findById(Long id) {
            return repository.findById(id)
                    .orElseThrow(() -> new NotFoundException("El ProductoCantidad no existe con id: " + id));
    }

    @Override
    @Transactional
    public ProductoCantidad save(ProductoCantidad t) {
            return repository.save(t);
    }

    @Override
    @Transactional
    public List<ProductoCantidad> saveAll(List<ProductoCantidad> list) {
            return (List<ProductoCantidad>) repository.saveAll(list);
    }
}