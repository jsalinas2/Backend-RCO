package com.pierrecode.debtcontrolapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pierrecode.debtcontrolapi.dto.LoginDTO;
import com.pierrecode.debtcontrolapi.dto.RegistroDTO;
import com.pierrecode.debtcontrolapi.dto.UsuarioDTO;
import com.pierrecode.debtcontrolapi.model.entity.Usuario;
import com.pierrecode.debtcontrolapi.repository.UsuarioRepository;
import com.pierrecode.debtcontrolapi.security.JwtTokenProvider;

@Service
public class UsuarioService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private EmailService emailService;

    public String autenticar(LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getContrasena()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return tokenProvider.generateToken(authentication);
    }

    public UsuarioDTO registrar(RegistroDTO registroDTO) {

        if (usuarioRepository.existsByEmail(registroDTO.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }


        Usuario usuario = new Usuario();
        usuario.setNombre(registroDTO.getNombre());
        usuario.setEmail(registroDTO.getEmail());
        usuario.setContrasena(passwordEncoder.encode(registroDTO.getContrasena()));

        Usuario nuevoUsuario = usuarioRepository.save(usuario);


        emailService.enviarEmailBienvenida(nuevoUsuario);

        return convertirADTO(nuevoUsuario);
    }

    public UsuarioDTO obtenerUsuarioActual() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return convertirADTO(usuario);
    }

    public UsuarioDTO actualizarUsuario(UsuarioDTO usuarioDTO) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setNombre(usuarioDTO.getNombre());


        if (!usuario.getEmail().equals(usuarioDTO.getEmail())) {
            if (usuarioRepository.existsByEmail(usuarioDTO.getEmail())) {
                throw new RuntimeException("El email ya está registrado");
            }
            usuario.setEmail(usuarioDTO.getEmail());
        }

        Usuario usuarioActualizado = usuarioRepository.save(usuario);

        return convertirADTO(usuarioActualizado);
    }

    public void cambiarContrasena(String contrasenaActual, String nuevaContrasena) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));


        if (!passwordEncoder.matches(contrasenaActual, usuario.getContrasena())) {
            throw new RuntimeException("La contraseña actual es incorrecta");
        }


        usuario.setContrasena(passwordEncoder.encode(nuevaContrasena));
        usuarioRepository.save(usuario);
    }

    private UsuarioDTO convertirADTO(Usuario usuario) {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(usuario.getId());
        usuarioDTO.setNombre(usuario.getNombre());
        usuarioDTO.setEmail(usuario.getEmail());
        return usuarioDTO;
    }
}
