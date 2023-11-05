package com.example.bancocom.controller;


import com.example.bancocom.dtos.PostDTO;
import com.example.bancocom.exceptions.PostNotFoundException;
import com.example.bancocom.exceptions.UnauthorizedException;
import com.example.bancocom.mapper.PostMapper;
import com.example.bancocom.model.Post;
import com.example.bancocom.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private PostMapper postMapper;

    @PostMapping("/")
    public ResponseEntity<PostDTO> crearPost(@RequestBody PostDTO postDTO, Authentication authentication) {
        Post post = postService.crearPost(postDTO, authentication);
        PostDTO creadoPostDTO = postMapper.convertirAPostDTO(post);
        return new ResponseEntity<>(creadoPostDTO, HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<PostDTO>> listarPosts(Authentication authentication) {
        List<PostDTO> posts = postService.listarPostsPorUsuario(authentication);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostDTO> actualizarPost(@PathVariable Long postId, @RequestBody PostDTO postDTO, Authentication authentication) {
        Post postActualizado = postService.actualizarPost(postId, postDTO, authentication);
        PostDTO actualizadoPostDTO = postMapper.convertirAPostDTO(postActualizado);
        return ResponseEntity.ok(actualizadoPostDTO);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<?> eliminarPost(@PathVariable Long postId, Authentication authentication) {
        postService.eliminarPost(postId, authentication);
        return ResponseEntity.ok().build();
    }

    // Manejador de excepciones para PostNotFoundException
    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<?> manejarPostNoEncontrado(PostNotFoundException ex) {
        return ResponseEntity.notFound().build();
    }

    // Manejador de excepciones para UnauthorizedException
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<?> manejarNoAutorizado(UnauthorizedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }
}
