package com.example.bancocom.repository;


import com.example.bancocom.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    // Encuentra un Post por su ID y el ID del usuario
    Optional<Post> findByIdAndUsuarioId(Long id, Long usuarioId);

    // Encuentra todos los Posts asociados con un usuario específico
    List<Post> findByUsuarioId(Long usuarioId);

    List<Post> findAllByUsuarioId(Long usuarioId);

}
