package com.example.bancocom.service;

import com.example.bancocom.dtos.ActualizarUsuarioDTO;
import com.example.bancocom.dtos.CrearUsuarioDTO;
import com.example.bancocom.dtos.UsuarioDTO;
import com.example.bancocom.exceptions.UnauthorizedException;
import com.example.bancocom.exceptions.UsuarioNotFoundException;
import com.example.bancocom.mapper.UsuarioMapper;
import com.example.bancocom.model.Usuario;
import com.example.bancocom.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioMapper usuarioMapper;

    public List<UsuarioDTO> listarTodosLosUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream()
                .map(this.usuarioMapper::convertirAUsuarioDTO)
                .collect(Collectors.toList());
    }

    public Usuario crearUsuario(CrearUsuarioDTO crearUsuarioDTO) {
        Usuario usuario = new Usuario();
        usuario.setCellphone(crearUsuarioDTO.getCellphone());
        usuario.setName(crearUsuarioDTO.getName());
        usuario.setLastName(crearUsuarioDTO.getLastName());
        usuario.setUsername(crearUsuarioDTO.getUsername());
        usuario.setPassword(passwordEncoder.encode(crearUsuarioDTO.getPassword())); // Asegúrate de encriptar la contraseña
        // Otras asignaciones necesarias

        return usuarioRepository.save(usuario);
    }


    public Usuario crearUsuario(Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }

    public Usuario actualizarUsuario(Long id, ActualizarUsuarioDTO actualizarUsuarioDTO, Authentication authentication) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuario no encontrado"));

        if (!authentication.getName().equals(usuario.getUsername())) {
            throw new UnauthorizedException("No está autorizado para actualizar este usuario");
        }

        usuario.setCellphone(actualizarUsuarioDTO.getCellphone());
        usuario.setName(actualizarUsuarioDTO.getName());
        usuario.setLastName(actualizarUsuarioDTO.getLastName());

        return usuarioRepository.save(usuario);
    }


    public void eliminarUsuario(Long id, Authentication authentication) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuario no encontrado con id: " + id));

        String usernameActual = authentication.getName();
        if (!usuario.getUsername().equals(usernameActual)) {
            throw new UnauthorizedException("No autorizado para eliminar este usuario");
        }

        usuarioRepository.delete(usuario);
    }

    public Usuario obtenerUsuarioActual(Authentication authentication) {
        String username = authentication.getName();
        return usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuario no encontrado con username: " + username));
    }
}
