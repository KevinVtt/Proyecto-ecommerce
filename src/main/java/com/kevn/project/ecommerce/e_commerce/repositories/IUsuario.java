package com.kevn.project.ecommerce.e_commerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kevn.project.ecommerce.e_commerce.models.Usuario;

public interface IUsuario extends JpaRepository<Usuario,Long> {

}
