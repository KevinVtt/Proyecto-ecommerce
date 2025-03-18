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

    private final IProducto repository;

    public ProductoService(IProducto repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!this.repository.existsById(id)) {
            throw new NotFoundException("No se ha encontrado el producto para eliminarlo, ID: " + id);
        }
        this.repository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> findAll() {
        //es code smell retornar una entidad de persistencia
        //recomiendo retornar un DTO
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Producto findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("El producto no existe con id: " + id));
    }

    @Override
    @Transactional
    public Producto save(Producto t) {
/*        Optional<Producto> optional;
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
        //recorrer la lista de productos y verificar si ya existe el producto es completamente ineficiente
        imaginate que tenes 10 millones de productos, cargarlos en memoria te va a hacer explotar el sistema(no tan asi per bueno)

        */
        if(repository.existsByNombre(t.getNombre())){
            throw new AlreadyExistException("El producto que estás intentando insertar ya existe en la base de datos");
        }
        return repository.save(t);
    }

    @Override
    @Transactional
    public List<Producto> saveAll(List<Producto> list) {
        return  repository.saveAll(list);
    }
}