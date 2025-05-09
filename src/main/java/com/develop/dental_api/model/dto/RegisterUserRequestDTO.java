package com.develop.dental_api.model.dto;

import java.time.LocalDate;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegisterUserRequestDTO {
    private String email;
    private String password;
    private String role;
    private String firstName;
    private String lastName;
    private String phone;
    private LocalDate birthDate;
    private String dni;
}
