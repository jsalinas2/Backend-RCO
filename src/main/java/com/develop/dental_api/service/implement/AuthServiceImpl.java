package com.develop.dental_api.service.implement;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.develop.dental_api.model.dto.ChangePasswordDTO;
import com.develop.dental_api.model.dto.LoginRequestDTO;
import com.develop.dental_api.model.dto.LoginResponseDTO;
import com.develop.dental_api.model.dto.MessageResponseDTO;
import com.develop.dental_api.model.dto.RegisterUserRequestDTO;
import com.develop.dental_api.model.dto.RegisterUserResponseDTO;
import com.develop.dental_api.model.dto.ResetPasswordDTO;
import com.develop.dental_api.model.entity.ClinicalRecord;
import com.develop.dental_api.model.entity.Profile;
import com.develop.dental_api.model.entity.User;
import com.develop.dental_api.model.enums.Role;
import com.develop.dental_api.model.mapper.UserMapper;
import com.develop.dental_api.repository.ClinicalRecordRepository;
import com.develop.dental_api.repository.ProfileRepository;
import com.develop.dental_api.repository.UserRepository;
import com.develop.dental_api.security.JwtService;
import com.develop.dental_api.service.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final UserMapper userMapper;
    private final ClinicalRecordRepository clinicalRecordRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;
    
    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${app.frontend.url}")
    private String url;

    @Override
    public RegisterUserResponseDTO register(RegisterUserRequestDTO dto) {
        
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Ya existe un usuario registrado con ese email.");
        }

        
        if (profileRepository.existsByDni(dto.getDni())) {
            throw new IllegalArgumentException("Ya existe un perfil registrado con ese DNI.");
        }

        
        User user = userMapper.toUserEntity(dto);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(Role.valueOf(dto.getRole().toUpperCase()));
        user = userRepository.save(user);

        
        Profile profile = userMapper.toProfileEntity(dto, user);
        profileRepository.save(profile);

        
        ClinicalRecord clinicalRecord = new ClinicalRecord();
        clinicalRecord.setUser(user);
        clinicalRecord.setCreatedAt(LocalDateTime.now());
        clinicalRecordRepository.save(clinicalRecord);

        // Enviar correo de bienvenida
        try {
            Context context = new Context();
            context.setVariable("nombre", profile.getFirstName());

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(user.getEmail());
            helper.setSubject("Bienvenido a Dental Esthetic");
            helper.setText(templateEngine.process("correo-bienvenida.html", context), true);

            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace(); // Loguea el error, pero no detiene el registro
        }

        return new RegisterUserResponseDTO("Usuario registrado exitosamente", user.getUserId());
    }
    
    @Override
    public LoginResponseDTO login(LoginRequestDTO dto) {
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        
        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole().name()) 
                .build();

        String token = jwtService.generateToken(userDetails);

        return new LoginResponseDTO(token, userMapper.toUserLoginDTO(user));
    }

    @Override
    public MessageResponseDTO sendRecoveryEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("No existe usuario con ese email"));

        // Generar token temporal
        String recoveryToken = generateRecoveryToken();
        
        // Guardar token en usuario
        user.setRecoveryToken(recoveryToken);
        user.setRecoveryTokenExpiry(LocalDateTime.now().plusHours(1)); // Token válido por 1 hora
        userRepository.save(user);

        // Construir el link de recuperación
        String resetLink = url + "/reset-password?token=" + recoveryToken;

        // Enviar email HTML
        try {
            Context context = new Context();
            context.setVariable("resetLink", resetLink);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(email);
            helper.setSubject("Recuperación de Contraseña");
            helper.setText(templateEngine.process("correo-recuperacion.html", context), true);

            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("No se pudo enviar el correo de recuperación");
        }

        return new MessageResponseDTO("Se ha enviado un correo con las instrucciones para recuperar tu contraseña");
    }

    private String generateRecoveryToken() {
        return UUID.randomUUID().toString();
    }

    @Override
    public MessageResponseDTO changePassword(ChangePasswordDTO dto) {
        // Obtener el usuario autenticado (asumiendo que tienes SecurityContextHolder configurado)
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Verificar la contraseña actual
        if (!passwordEncoder.matches(dto.getCurrentPassword(), user.getPassword())) {
            throw new RuntimeException("La contraseña actual es incorrecta");
        }

        // Validar que la nueva contraseña no sea igual a la actual
        if (passwordEncoder.matches(dto.getNewPassword(), user.getPassword())) {
            throw new RuntimeException("La nueva contraseña debe ser diferente a la actual");
        }

        // Encriptar y guardar la nueva contraseña
        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        userRepository.save(user);

        return new MessageResponseDTO("Contraseña actualizada correctamente");
    }

    @Override
    public MessageResponseDTO resetPassword(ResetPasswordDTO dto) {
        User user = userRepository.findByRecoveryToken(dto.getToken())
                .orElseThrow(() -> new RuntimeException("Token inválido o expirado"));

        // Verificar si el token ha expirado
        if (user.getRecoveryTokenExpiry().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("El token ha expirado");
        }

        // Actualizar contraseña
        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        // Limpiar token de recuperación
        user.setRecoveryToken(null);
        user.setRecoveryTokenExpiry(null);
        
        userRepository.save(user);

        return new MessageResponseDTO("Contraseña actualizada correctamente");
    }
}
