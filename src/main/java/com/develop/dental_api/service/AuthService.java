package com.develop.dental_api.service;

import com.develop.dental_api.model.dto.ChangePasswordDTO;
import com.develop.dental_api.model.dto.LoginRequestDTO;
import com.develop.dental_api.model.dto.LoginResponseDTO;
import com.develop.dental_api.model.dto.MessageResponseDTO;
import com.develop.dental_api.model.dto.RegisterUserRequestDTO;
import com.develop.dental_api.model.dto.RegisterUserResponseDTO;

public interface AuthService {

    RegisterUserResponseDTO register(RegisterUserRequestDTO dto);

    LoginResponseDTO login(LoginRequestDTO dto);

    MessageResponseDTO sendRecoveryEmail(String email);

    MessageResponseDTO changePassword(ChangePasswordDTO dto);
}