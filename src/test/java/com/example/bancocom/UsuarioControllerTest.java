package com.example.bancocom;

import com.example.bancocom.controller.UsuarioController;
import com.example.bancocom.dtos.ActualizarUsuarioDTO;
import com.example.bancocom.dtos.CrearUsuarioDTO;
import com.example.bancocom.dtos.UsuarioDTO;
import com.example.bancocom.mapper.UsuarioMapper;
import com.example.bancocom.model.Usuario;
import com.example.bancocom.service.UsuarioService;
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

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioControllerTest {

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private UsuarioMapper usuarioMapper;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private UsuarioController usuarioController;

    private Usuario usuario;
    private UsuarioDTO usuarioDTO;
    private CrearUsuarioDTO crearUsuarioDTO;
    private ActualizarUsuarioDTO actualizarUsuarioDTO;

    @BeforeEach
    void setUp() {
        // Inicializa tus objetos aquí
        usuario = new Usuario();
        usuarioDTO = new UsuarioDTO();
        crearUsuarioDTO = new CrearUsuarioDTO();
        actualizarUsuarioDTO = new ActualizarUsuarioDTO();
        // Set properties as needed
    }

    @Test
    void listarTodosLosUsuariosTest() {
        // Arrange
        List<UsuarioDTO> listaUsuariosDTO = Arrays.asList(usuarioDTO);
        when(usuarioService.listarTodosLosUsuarios()).thenReturn(listaUsuariosDTO);

        // Act
        ResponseEntity<List<UsuarioDTO>> response = usuarioController.listarTodosLosUsuarios();

        // Assert
        assertNotNull(response);
        assertEquals(OK, response.getStatusCode());
        assertEquals(listaUsuariosDTO, response.getBody());
    }

    @Test
    void crearUsuarioTest() {
        // Arrange
        when(usuarioService.crearUsuario(any(CrearUsuarioDTO.class))).thenReturn(usuario);
        when(usuarioMapper.convertirAUsuarioDTO(any(Usuario.class))).thenReturn(usuarioDTO);

        // Act
        ResponseEntity<UsuarioDTO> response = usuarioController.crearUsuario(crearUsuarioDTO);

        // Assert
        assertNotNull(response);
        assertEquals(CREATED, response.getStatusCode());
        assertEquals(usuarioDTO, response.getBody());
    }

    @Test
    void actualizarUsuarioTest() {
        // Arrange
        when(usuarioService.actualizarUsuario(anyLong(), any(ActualizarUsuarioDTO.class), any(Authentication.class))).thenReturn(usuario);
        when(usuarioMapper.convertirAUsuarioDTO(any(Usuario.class))).thenReturn(usuarioDTO);

        // Act
        ResponseEntity<UsuarioDTO> response = usuarioController.actualizarUsuario(1L, actualizarUsuarioDTO, authentication);

        // Assert
        assertNotNull(response);
        assertEquals(OK, response.getStatusCode());
        assertEquals(usuarioDTO, response.getBody());
    }

    @Test
    void eliminarUsuarioTest() {
        // Act
        ResponseEntity<?> response = usuarioController.eliminarUsuario(1L, authentication);

        // Assert
        assertNotNull(response);
        assertEquals(OK, response.getStatusCode());
        verify(usuarioService, times(1)).eliminarUsuario(anyLong(), any(Authentication.class));
    }
}
