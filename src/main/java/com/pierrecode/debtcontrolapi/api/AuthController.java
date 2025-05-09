package com.pierrecode.debtcontrolapi.api;

import jakarta.validation.Valid;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pierrecode.debtcontrolapi.dto.LoginDTO;
import com.pierrecode.debtcontrolapi.dto.RegistroDTO;
import com.pierrecode.debtcontrolapi.dto.UsuarioDTO;
import com.pierrecode.debtcontrolapi.service.UsuarioService;

@CrossOrigin(origins = "https://debttrackerweb.netlify.app")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@Valid @RequestBody LoginDTO loginDTO) {
        String token = usuarioService.autenticar(loginDTO);

        JwtAuthResponse response = new JwtAuthResponse();
        response.setAccessToken(token);
        response.setTokenType("Bearer");
        response.setMensaje("Inicio de sesión exitoso");

        return ResponseEntity.ok(response);
    }

    @PostMapping("/registro")
    public ResponseEntity<RegistroResponse> registro(@Valid @RequestBody RegistroDTO registroDTO) {
        UsuarioDTO usuarioDTO = usuarioService.registrar(registroDTO);

        RegistroResponse response = new RegistroResponse();
        response.setUsuario(usuarioDTO);
        response.setMensaje("Usuario registrado exitosamente");

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Getter
    @Setter
    // Clase interna para la respuesta de autenticación
    public static class JwtAuthResponse {
        private String accessToken;
        private String tokenType;
        private String mensaje;

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public String getTokenType() {
            return tokenType;
        }

        public void setTokenType(String tokenType) {
            this.tokenType = tokenType;
        }

        public String getMensaje() {
            return mensaje;
        }

        public void setMensaje(String mensaje) {
            this.mensaje = mensaje;
        }
    }


    @Getter
    @Setter
    // Clase interna para la respuesta de registro
    public static class RegistroResponse {
        private UsuarioDTO usuario;
        private String mensaje;

        public UsuarioDTO getUsuario() {
            return usuario;
        }

        public void setUsuario(UsuarioDTO usuario) {
            this.usuario = usuario;
        }

        public String getMensaje() {
            return mensaje;
        }

        public void setMensaje(String mensaje) {
            this.mensaje = mensaje;
        }
    }
}
