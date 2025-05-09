package com.pierrecode.debtcontrolapi.config;

import com.pierrecode.debtcontrolapi.model.entity.CategoriaDeuda;
import com.pierrecode.debtcontrolapi.model.entity.Usuario;
import com.pierrecode.debtcontrolapi.repository.CategoriaDeudaRepository;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.pierrecode.debtcontrolapi.repository.UsuarioRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private CategoriaDeudaRepository categoriaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Inicializar categorías predefinidas
        
        if (categoriaRepository.count() == 0) {
            List<CategoriaDeuda> categorias = Arrays.asList(
                    new CategoriaDeuda(0, "Proveedores", null),
                    new CategoriaDeuda(0, "Impuestos", null),
                    new CategoriaDeuda(0, "Prestamos", null),
                    new CategoriaDeuda(0, "Servicios", null),
                    new CategoriaDeuda(0, "Otros", null)
            );

            categoriaRepository.saveAll(categorias);
        }

        // Crear usuario de prueba
        if (usuarioRepository.count() == 0) {
            Usuario usuario = new Usuario(
                    0, "Usuario Demo",
                    "usuario@ejemplo.com",
                    passwordEncoder.encode("123456"), null
            );

            usuarioRepository.save(usuario);
        }

         
    }
}
