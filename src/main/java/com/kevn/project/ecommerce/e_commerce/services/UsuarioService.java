package com.kevn.project.ecommerce.e_commerce.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.kevn.project.ecommerce.e_commerce.exception.AlreadyExistException;
import com.kevn.project.ecommerce.e_commerce.exception.NotFoundException;
import com.kevn.project.ecommerce.e_commerce.models.Usuario;
import com.kevn.project.ecommerce.e_commerce.repositories.IUsuario;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService implements IService<Usuario> {

    @Autowired
    private IUsuario repository;

    @Override
    @Transactional
    public void delete(Long id) {

        Optional<Usuario> optional = Optional.of(findById(id));
        if (optional.isPresent()) {
            repository.delete(optional.orElseThrow());
        }
        throw new NotFoundException("No se ha encontrado el usuario para eliminarlo, ID: " + id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> findAll() {

        return (List<Usuario>) repository.findAll();

    }

    @Override
    @Transactional(readOnly = true)
    public Usuario findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("El objeto no existe"));
    }

    @Override
    @Transactional
    public Usuario save(Usuario t) {

        Optional<Usuario> userDb;

        if (t.getId() != null) {
            userDb = Optional.of(findById(t.getId()));
            if (userDb.isPresent()) {
                return repository.save(t);
            }

            throw new NotFoundException("El usuario no existe en la base de datos para modificarlo");


        } else {
            userDb = findAll()
                    .stream()
                    .filter(u -> u.getNombre().equals(t.getNombre()) && u.getDni().equals(t.getDni()))
                    .findAny();

            if (userDb.isEmpty()) {
                return repository.save(t);
            }

            throw new AlreadyExistException("el objeto ya existe " + " nombre: " + t.getNombre()
                    + " email: " + t.getEmail());
        }

    }

    @Override
    @Transactional
    public List<Usuario> saveAll(List<Usuario> list) {

        return (List<Usuario>) repository.saveAll(list);

    }


}
