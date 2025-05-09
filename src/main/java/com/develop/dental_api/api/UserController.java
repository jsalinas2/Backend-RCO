package com.develop.dental_api.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.develop.dental_api.model.dto.MessageResponseDTO;
import com.develop.dental_api.model.dto.UpdateProfileDTO;
import com.develop.dental_api.model.dto.UserAppointmentDTO;
import com.develop.dental_api.model.dto.UserPaymentDTO;
import com.develop.dental_api.model.dto.UserProfileDTO;
import com.develop.dental_api.service.UserService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "https://odontologiaweb.netlify.app")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{user_id}/appointments")
    public ResponseEntity<List<UserAppointmentDTO>> getUserAppointments(@PathVariable Integer user_id) {
        return ResponseEntity.ok(userService.getAppointments(user_id));
    }

    @GetMapping("/{user_id}/payments")
    public ResponseEntity<List<UserPaymentDTO>> getUserPayments(@PathVariable Integer user_id) {
        return ResponseEntity.ok(userService.getPayments(user_id));
    }

    @GetMapping("/{user_id}/profile")
    public ResponseEntity<UserProfileDTO> getProfile(@PathVariable Integer user_id) {
        return ResponseEntity.ok(userService.getProfile(user_id));
    }

    @PutMapping("/{user_id}/profile")
    public ResponseEntity<MessageResponseDTO> updateProfile(
            @PathVariable Integer user_id,
            @RequestBody UpdateProfileDTO request) {
        return ResponseEntity.ok(userService.updateProfile(user_id, request));
    }
}
