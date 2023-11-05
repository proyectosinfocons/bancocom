package com.example.bancocom.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CrearUsuarioDTO {
    private String cellphone;
    private String name;
    private String lastName;
    private String username;
    private String password; // Se incluye aquí porque es necesario para la creación
}
