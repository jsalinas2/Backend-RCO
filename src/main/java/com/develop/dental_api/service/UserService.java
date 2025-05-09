package com.develop.dental_api.service;

import java.util.List;

import com.develop.dental_api.model.dto.MessageResponseDTO;
import com.develop.dental_api.model.dto.UpdateProfileDTO;
import com.develop.dental_api.model.dto.UserAppointmentDTO;
import com.develop.dental_api.model.dto.UserPaymentDTO;
import com.develop.dental_api.model.dto.UserProfileDTO;

public interface UserService {

    List<UserAppointmentDTO> getAppointments(Integer userId);

    List<UserPaymentDTO> getPayments(Integer userId);

    UserProfileDTO getProfile(Integer userId);

    MessageResponseDTO updateProfile(Integer userId, UpdateProfileDTO dto);
}
