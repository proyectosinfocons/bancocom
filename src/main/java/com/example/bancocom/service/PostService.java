package com.example.bancocom.service;

import com.example.bancocom.dtos.PostDTO;
import com.example.bancocom.exceptions.PostNotFoundException;
import com.example.bancocom.exceptions.UnauthorizedException;
import com.example.bancocom.mapper.PostMapper;
import com.example.bancocom.model.Post;
import com.example.bancocom.repository.PostRepository;
import com.example.bancocom.repository.UsuarioRepository;
import com.example.bancocom.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PostMapper postMapper;

    public Post crearPost(PostDTO postDTO, Authentication authentication) {
        Usuario usuario = obtenerUsuarioDesdeAuthentication(authentication);
        Post post = new Post();
        post.setText(postDTO.getText());
        post.setUsuario(usuario);
        return postRepository.save(post);
    }

    public Post actualizarPost(Long postId, PostDTO postDTO, Authentication authentication) {
        Usuario usuario = obtenerUsuarioDesdeAuthentication(authentication);
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException("Post no encontrado con id: " + postId));
        if (!post.getUsuario().getId().equals(usuario.getId())) {
            throw new UnauthorizedException("No autorizado para modificar este post");
        }
        post.setText(postDTO.getText());
        return postRepository.save(post);
    }

    public void eliminarPost(Long postId, Authentication authentication) {
        Usuario usuario = obtenerUsuarioDesdeAuthentication(authentication);
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException("Post no encontrado con id: " + postId));
        if (!post.getUsuario().getId().equals(usuario.getId())) {
            throw new UnauthorizedException("No autorizado para eliminar este post");
        }
        postRepository.delete(post);
    }

    public List<PostDTO> listarPostsPorUsuario(Authentication authentication) {
        Usuario usuario = obtenerUsuarioDesdeAuthentication(authentication);
        List<Post> posts = postRepository.findAllByUsuarioId(usuario.getId());
        return posts.stream().map(this.postMapper::convertirAPostDTO).collect(Collectors.toList());
    }

    private Usuario obtenerUsuarioDesdeAuthentication(Authentication authentication) {
        String username = authentication.getName();
        return usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UnauthorizedException("Usuario no encontrado"));
    }
}
