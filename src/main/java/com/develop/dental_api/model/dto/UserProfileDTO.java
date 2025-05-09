package com.develop.dental_api.model.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserProfileDTO {
    private Integer userId;
    private String firstName;
    private String lastName;
    private String phone;
    private LocalDate birthDate;
    private String dni;
    private String role;
}