package com.kevn.project.ecommerce.e_commerce.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.kevn.project.ecommerce.e_commerce.exception.NotFoundException;
import com.kevn.project.ecommerce.e_commerce.models.Pedido;
import com.kevn.project.ecommerce.e_commerce.repositories.IPedido;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PedidoService implements IService<Pedido> {

    @Autowired
    private IPedido repository;

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
            // Si el ID es mayor que 0, es una actualización
            if (pedido.getId() != null && pedido.getId() > 0) {
                Optional<Pedido> pedidoDb = repository.findById(pedido.getId());
                if (pedidoDb.isPresent()) {
                    return repository.save(pedido); // Actualiza el pedido existente
                } else {
                    throw new NotFoundException("No se ha encontrado el pedido para modificarlo");
                }
            } else {
                return repository.save(pedido); // Guarda el nuevo pedido

            }
    }

    @Override
    public List<Pedido> saveAll(List<Pedido> list) {

            return (List<Pedido>) repository.saveAll(list);

    }

}
