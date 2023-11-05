package com.example.bancocom.mapper;

import com.example.bancocom.dtos.UsuarioDTO;
import com.example.bancocom.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {
    public UsuarioDTO convertirAUsuarioDTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setCellphone(usuario.getCellphone());
        dto.setName(usuario.getName());
        dto.setLastName(usuario.getLastName());
        dto.setUsername(usuario.getUsername());
        // No incluir la contraseña
        return dto;
    }
}
