package com.pierrecode.debtcontrolapi.api;

import com.pierrecode.debtcontrolapi.dto.UsuarioDTO;
import com.pierrecode.debtcontrolapi.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "https://debttrackerweb.netlify.app")
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/me")
    public ResponseEntity<UsuarioDTO> obtenerUsuarioActual() {
        UsuarioDTO usuarioDTO = usuarioService.obtenerUsuarioActual();
        return ResponseEntity.ok(usuarioDTO);
    }

    @PutMapping("/me")
    public ResponseEntity<UsuarioDTO> actualizarUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        UsuarioDTO usuarioActualizado = usuarioService.actualizarUsuario(usuarioDTO);
        return ResponseEntity.ok(usuarioActualizado);
    }

    @PutMapping("/me/contrasena")
    public ResponseEntity<MensajeResponse> cambiarContrasena(@RequestBody CambioContrasenaRequest request) {
        usuarioService.cambiarContrasena(request.getContrasenaActual(), request.getNuevaContrasena());

        MensajeResponse response = new MensajeResponse();
        response.setMensaje("Contraseña actualizada exitosamente");

        return ResponseEntity.ok(response);
    }

    @Getter
    @Setter
    // Clase interna para la solicitud de cambio de contraseña
    public static class CambioContrasenaRequest {
        private String contrasenaActual;
        private String nuevaContrasena;

        public String getContrasenaActual() {
            return contrasenaActual;
        }

        public void setContrasenaActual(String contrasenaActual) {
            this.contrasenaActual = contrasenaActual;
        }

        public String getNuevaContrasena() {
            return nuevaContrasena;
        }

        public void setNuevaContrasena(String nuevaContrasena) {
            this.nuevaContrasena = nuevaContrasena;
        }
    }

    @Getter
    @Setter
    // Clase interna para la respuesta de mensaje
    public static class MensajeResponse {
        private String mensaje;

        public String getMensaje() {
            return mensaje;
        }

        public void setMensaje(String mensaje) {
            this.mensaje = mensaje;
        }
    }
}
