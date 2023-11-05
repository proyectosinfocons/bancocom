package com.example.bancocom.run;

import com.example.bancocom.model.Usuario;
import com.example.bancocom.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public void run(String... args) throws Exception {
        cargarDatosUsuarios();
    }

    private void cargarDatosUsuarios() {
        if (usuarioRepository.count() == 0) {
            Usuario usuario = new Usuario();
            usuario.setCellphone("1234567890");
            usuario.setName("Juan");
            usuario.setLastName("Perez");
            usuario.setUsername("juanperez");
            usuario.setPassword(passwordEncoder.encode("contrasenia"));

            usuarioRepository.save(usuario);

            // Crea y guarda más usuarios si es necesario
        }
    }
}
