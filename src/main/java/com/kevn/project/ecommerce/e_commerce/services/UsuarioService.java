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

        try {
            Optional<Usuario> optional = Optional.of(findById(id));
            if(optional.isPresent()){
                repository.delete(optional.orElseThrow());
            }
            throw new NotFoundException("No se ha encontrado el usuario para eliminarlo, ID: " + id);
        } catch (Exception e) {
            throw new RuntimeException("error al eliminar el usuario con id " + id + " mensaje de error: " + e.getMessage());
        }

    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> findAll() {
        
        try {
            return (List<Usuario>) repository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("error al buscar todos los usuarios " + e.getMessage());
        }
        
    }
    
    @Override
    @Transactional(readOnly = true)
    public Usuario findById(Long id) {
        
        try {
            return repository.findById(id).orElseThrow(() -> new NotFoundException("El objeto no existe"));
        } catch (Exception e) {
            throw new NotFoundException(
                "error al buscar el usuario con id " + id + " mensaje de error: " + e.getMessage());
            }
        }
        
        @Override
        @Transactional
        public Usuario save(Usuario t) {
            
        try {
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

        } catch (AlreadyExistException e) {
            throw new AlreadyExistException(e.getMessage());
        } catch (Exception e) {
            throw new NotFoundException(
                    "error al guardar/insertar el usuario " + t + " mensaje de error: " + e.getMessage());
        }

    }

        @Override
        @Transactional
        public List<Usuario> saveAll(List<Usuario> list) {
            try{

                return (List<Usuario>)repository.saveAll(list);

            }catch(Exception e){
                throw e;
            }
        }

    

}
