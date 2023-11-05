package com.example.bancocom.repository;

import com.example.bancocom.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUsername(String username);


    Optional<Usuario> findByCellphone(String cellphone);

}
