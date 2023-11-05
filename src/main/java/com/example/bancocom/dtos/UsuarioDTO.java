package com.example.bancocom.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDTO {
    private Long id;
    private String cellphone;
    private String name;
    private String lastName;
    private String username;
}

