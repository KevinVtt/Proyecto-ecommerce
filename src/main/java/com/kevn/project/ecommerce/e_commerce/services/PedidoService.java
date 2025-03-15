package com.kevn.project.ecommerce.e_commerce.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.kevn.project.ecommerce.e_commerce.exception.AlreadyExistException;
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
        try {
            Optional<Pedido> pedido = Optional.of(findById(id));
            if (pedido.isPresent()) {
                repository.delete(pedido.orElseThrow());
            }
            throw new NotFoundException("No se ha encontrado el producto para eliminarlo, ID: " + id);
        } catch (Exception e) {
            throw new RuntimeException("error al eliminar con id " + id + " mensaje de error: " + e.getMessage());
        }

    }

    @Override
    @Transactional(readOnly = true)
    public List<Pedido> findAll() {
        
        try {
            return (List<Pedido>) repository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar todos los pedidos " + e.getMessage());
        }
        
    }
    
    @Override
    @Transactional(readOnly = true)
    public Pedido findById(Long id) {
        
        try {
            return repository.findById(id).orElseThrow(() -> new NotFoundException("El objeto no existe"));
        } catch (Exception e) {
            throw new NotFoundException(
                    "error al buscar el pedido con id " + id + " mensaje de error " + e.getMessage());
        }

    }

    @Override
    @Transactional
    public Pedido save(Pedido pedido) {
        try {
            // Si el ID es mayor que 0, es una actualización
            if (pedido.getId() > 0) {
                Optional<Pedido> pedidoDb = repository.findById(pedido.getId());
                if (pedidoDb.isPresent()) {
                    return repository.save(pedido); // Actualiza el pedido existente
                } else {
                    throw new NotFoundException("No se ha encontrado el pedido para modificarlo");
                }
            } else {
                // Verifica si ya existe un pedido con el mismo ItemProducto y Usuario
                List<Pedido> lstPedido = (List<Pedido>)repository.findAll();
                boolean existePedido = lstPedido.stream()
                        .anyMatch(p -> p.getItemProducto().equals(pedido.getItemProducto()) &&
                                      p.getItemProducto().getUsuario().equals(pedido.getItemProducto().getUsuario()));
    
                if (existePedido) {
                    throw new AlreadyExistException("Error, ya existe un pedido con el mismo carrito y mismo usuario");
                } else {
                    return repository.save(pedido); // Guarda el nuevo pedido
                }
            }
        } catch (Exception e) {
            throw new NotFoundException("Error al guardar/insertar el pedido: " + e.getMessage());
        }
    }

    @Override
    public List<Pedido> saveAll(List<Pedido> list) {
        try{

            return (List<Pedido>)repository.saveAll(list);

        }catch(Exception e){
            throw e;
        }
    }

    

}
