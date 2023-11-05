package com.example.bancocom;

import com.example.bancocom.controller.PostController;
import com.example.bancocom.dtos.PostDTO;
import com.example.bancocom.mapper.PostMapper;
import com.example.bancocom.model.Post;
import com.example.bancocom.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.*;

@ExtendWith(MockitoExtension.class)
public class PostControllerTest {

    @Mock
    private PostService postService;

    @Mock
    private PostMapper postMapper;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private PostController postController;

    private Post post;
    private PostDTO postDTO;

    @BeforeEach
    void setUp() {
        post = new Post();
        postDTO = new PostDTO();
        // Set properties as needed
    }

    @Test
    void crearPostTest() {
        // Arrange
        when(postService.crearPost(any(PostDTO.class), any(Authentication.class))).thenReturn(post);
        when(postMapper.convertirAPostDTO(any(Post.class))).thenReturn(postDTO);

        // Act
        ResponseEntity<PostDTO> response = postController.crearPost(postDTO, authentication);

        // Assert
        assertNotNull(response);
        assertEquals(CREATED, response.getStatusCode());
        assertEquals(postDTO, response.getBody());
    }

    @Test
    void listarPostsTest() {
        // Arrange
        List<PostDTO> listaPostsDTO = Arrays.asList(postDTO);
        when(postService.listarPostsPorUsuario(any(Authentication.class))).thenReturn(listaPostsDTO);

        // Act
        ResponseEntity<List<PostDTO>> response = postController.listarPosts(authentication);

        // Assert
        assertNotNull(response);
        assertEquals(OK, response.getStatusCode());
        assertEquals(listaPostsDTO, response.getBody());
    }

    @Test
    void actualizarPostTest() {
        // Arrange
        when(postService.actualizarPost(anyLong(), any(PostDTO.class), any(Authentication.class))).thenReturn(post);
        when(postMapper.convertirAPostDTO(any(Post.class))).thenReturn(postDTO);

        // Act
        ResponseEntity<PostDTO> response = postController.actualizarPost(1L, postDTO, authentication);

        // Assert
        assertNotNull(response);
        assertEquals(OK, response.getStatusCode());
        assertEquals(postDTO, response.getBody());
    }

    @Test
    void eliminarPostTest() {
        // Act
        ResponseEntity<?> response = postController.eliminarPost(1L, authentication);

        // Assert
        assertNotNull(response);
        assertEquals(OK, response.getStatusCode());
        verify(postService, times(1)).eliminarPost(anyLong(), any(Authentication.class));
    }

}
