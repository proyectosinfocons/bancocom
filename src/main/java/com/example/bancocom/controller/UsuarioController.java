package com.example.bancocom.controller;

import com.example.bancocom.dtos.ActualizarUsuarioDTO;
import com.example.bancocom.dtos.CrearUsuarioDTO;
import com.example.bancocom.dtos.UsuarioDTO;
import com.example.bancocom.exceptions.UnauthorizedException;
import com.example.bancocom.exceptions.UsuarioNotFoundException;
import com.example.bancocom.mapper.UsuarioMapper;
import com.example.bancocom.model.Usuario;
import com.example.bancocom.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioMapper usuarioMapper;

    // Listar todos los usuarios
    @GetMapping("/")
    public ResponseEntity<List<UsuarioDTO>> listarTodosLosUsuarios() {
        List<UsuarioDTO> usuarios = usuarioService.listarTodosLosUsuarios();
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }


    @PostMapping("/")
    public ResponseEntity<UsuarioDTO> crearUsuario(@RequestBody CrearUsuarioDTO crearUsuarioDTO) {
        Usuario usuario = usuarioService.crearUsuario(crearUsuarioDTO);
        UsuarioDTO usuarioDTO = this.usuarioMapper.convertirAUsuarioDTO(usuario);
        return new ResponseEntity<>(usuarioDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> actualizarUsuario(@PathVariable Long id, @RequestBody ActualizarUsuarioDTO actualizarUsuarioDTO, Authentication authentication) {
        Usuario usuarioActualizado = usuarioService.actualizarUsuario(id, actualizarUsuarioDTO, authentication);
        UsuarioDTO usuarioDTO = this.usuarioMapper.convertirAUsuarioDTO(usuarioActualizado);
        return ResponseEntity.ok(usuarioDTO);
    }

    // Eliminar un usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long id, Authentication authentication) {
        usuarioService.eliminarUsuario(id, authentication);
        return ResponseEntity.ok().build();
    }

    // Obtener el usuario actual (el que está autenticado)
    @GetMapping("/me")
    public ResponseEntity<Usuario> obtenerUsuarioActual(Authentication authentication) {
        Usuario usuarioActual = usuarioService.obtenerUsuarioActual(authentication);
        return ResponseEntity.ok(usuarioActual);
    }

    // Manejador de excepciones para UsuarioNotFoundException
    @ExceptionHandler(UsuarioNotFoundException.class)
    public ResponseEntity<?> manejarUsuarioNoEncontrado(UsuarioNotFoundException ex) {
        return ResponseEntity.notFound().build();
    }

    // Manejador de excepciones para UnauthorizedException
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<?> manejarNoAutorizado(UnauthorizedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }
}
