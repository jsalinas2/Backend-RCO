package com.develop.dental_api.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.develop.dental_api.model.dto.ChangePasswordDTO;
import com.develop.dental_api.model.dto.ForgotPasswordDTO;
import com.develop.dental_api.model.dto.LoginRequestDTO;
import com.develop.dental_api.model.dto.LoginResponseDTO;
import com.develop.dental_api.model.dto.MessageResponseDTO;
import com.develop.dental_api.model.dto.RegisterUserRequestDTO;
import com.develop.dental_api.model.dto.RegisterUserResponseDTO;
import com.develop.dental_api.service.AuthService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "https://odontologiaweb.netlify.app")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<RegisterUserResponseDTO> register(@RequestBody RegisterUserRequestDTO request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<MessageResponseDTO> forgotPassword(@RequestBody ForgotPasswordDTO request) {
        return ResponseEntity.ok(authService.sendRecoveryEmail(request.getEmail()));
    }

    @PostMapping("/change-password")
    public ResponseEntity<MessageResponseDTO> changePassword(@RequestBody ChangePasswordDTO request) {
        return ResponseEntity.ok(authService.changePassword(request));
    }
}
