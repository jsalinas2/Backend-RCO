package com.develop.dental_api.model.dto;

import lombok.Data;

@Data
public class UpdateProfileDTO {
    private String firstName;
    private String lastName;
    private String phone;
}