package com.kevn.project.ecommerce.e_commerce.services;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kevn.project.ecommerce.e_commerce.exception.NotFoundException;
import com.kevn.project.ecommerce.e_commerce.models.ItemProducto;
import com.kevn.project.ecommerce.e_commerce.repositories.IItemProducto;

@Service
public class ItemProductoService implements IService<ItemProducto>{

    private final IItemProducto repository;

    public ItemProductoService(IItemProducto repository) {
        this.repository = repository;
    }

    @Transactional
    @Override
    public void delete(Long id) {
        Optional<ItemProducto> optional = repository.findById(id);
            if(optional.isPresent()){
                repository.deleteById(id);
            }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ItemProducto> findAll() {
            return  repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public ItemProducto findById(Long id) {
            return repository.findById(id)
                    .orElseThrow(() -> new NotFoundException("El ProductoCantidad no existe con id: " + id));
    }

    @Override
    @Transactional
    public ItemProducto save(ItemProducto t) {
            return repository.save(t);
    }

    @Override
    @Transactional
    public List<ItemProducto> saveAll(List<ItemProducto> list) {
            return (List<ItemProducto>) repository.saveAll(list);
    }
}