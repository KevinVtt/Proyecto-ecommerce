package com.kevn.project.ecommerce.e_commerce.services;

import java.util.List;
import java.util.Optional;

import com.kevn.project.ecommerce.e_commerce.exception.NotFoundException;
import com.kevn.project.ecommerce.e_commerce.models.Pedido;
import com.kevn.project.ecommerce.e_commerce.repositories.IPedido;

import lombok.extern.log4j.Log4j2;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
public class PedidoService implements IService<Pedido> {

    private IPedido repository;

    // private ItemProductoService itemProductoService;

    public PedidoService(IPedido repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Optional<Pedido> pedido = Optional.of(findById(id));
        if (pedido.isPresent()) {
            repository.delete(pedido.orElseThrow());
        }
        throw new NotFoundException("No se ha encontrado el producto para eliminarlo, ID: " + id);

    }

    @Override
    @Transactional(readOnly = true)
    public List<Pedido> findAll() {
        return (List<Pedido>) repository.findAll();

    }

    @Override
    @Transactional(readOnly = true)
    public Pedido findById(Long id) {

        return repository.findById(id).orElseThrow(() -> new NotFoundException("El objeto no existe"));

    }

    @Override
    @Transactional
    public Pedido save(Pedido pedido) {
        return repository.save(pedido);
    }

    @Override
    @Transactional
    public List<Pedido> saveAll(List<Pedido> list) {
        return repository.saveAll(list);

    }


}
