package com.kevn.project.ecommerce.e_commerce.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.kevn.project.ecommerce.e_commerce.exception.AlreadyExistException;
import com.kevn.project.ecommerce.e_commerce.exception.NotFoundException;
import com.kevn.project.ecommerce.e_commerce.models.Producto;
import com.kevn.project.ecommerce.e_commerce.repositories.IProducto;

@Service
public class ProductoService implements IService<Producto> {

    @Autowired
    private IProducto repository;

    @Override
    @Transactional
    public void delete(Long id) {
        try {
            Optional<Producto> producto = Optional.of(findById(id));
            if (producto.isPresent()) {
                repository.delete(producto.orElseThrow());
            } else {
                throw new NotFoundException("No se ha encontrado el producto para eliminarlo, ID: " + id);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar el producto con id " + id + ". Mensaje de error: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> findAll() {
        try {
            return (List<Producto>) repository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar todos los productos: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Producto findById(Long id) {
        try {
            return repository.findById(id)
                    .orElseThrow(() -> new NotFoundException("El producto no existe con id: " + id));
        } catch (Exception e) {
            throw new NotFoundException("Error al buscar el producto con id " + id + ". Mensaje de error: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public Producto save(Producto t) {
        try {
            Optional<Producto> optional;
            if (t.getId() != null && t.getId() > 0) {
                optional = Optional.of(findById(t.getId()));
                if (optional.isPresent()) {
                    return repository.save(t);
                } else {
                    throw new NotFoundException("No se ha encontrado el producto para realizar los cambios");
                }
            } else {
                optional = findAll().stream()
                        .filter(p -> p.getNombre().equals(t.getNombre()) && p.getTipo().equals(t.getTipo()))
                        .findFirst();
                if (optional.isPresent()) {
                    throw new AlreadyExistException("El producto que estás intentando insertar ya existe en la base de datos");
                }
                return repository.save(t);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar el producto: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public List<Producto> saveAll(List<Producto> list) {
        try {
            return (List<Producto>) repository.saveAll(list);
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar la lista de productos: " + e.getMessage());
        }
    }
}