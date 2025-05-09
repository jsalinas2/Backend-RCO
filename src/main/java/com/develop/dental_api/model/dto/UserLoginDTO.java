package com.develop.dental_api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserLoginDTO {
    private Integer id;
    private String email;
    private String role;
}