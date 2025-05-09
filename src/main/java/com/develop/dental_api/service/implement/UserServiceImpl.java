package com.develop.dental_api.service.implement;

import java.util.List;

import org.springframework.stereotype.Service;

import com.develop.dental_api.model.dto.MessageResponseDTO;
import com.develop.dental_api.model.dto.UpdateProfileDTO;
import com.develop.dental_api.model.dto.UserAppointmentDTO;
import com.develop.dental_api.model.dto.UserPaymentDTO;
import com.develop.dental_api.model.dto.UserProfileDTO;
import com.develop.dental_api.model.entity.Profile;
import com.develop.dental_api.model.entity.User;
import com.develop.dental_api.model.mapper.AppointmentMapper;
import com.develop.dental_api.model.mapper.PaymentMapper;
import com.develop.dental_api.model.mapper.ProfileMapper;
import com.develop.dental_api.model.mapper.UserMapper;
import com.develop.dental_api.repository.AppointmentRepository;
import com.develop.dental_api.repository.PaymentRepository;
import com.develop.dental_api.repository.ProfileRepository;
import com.develop.dental_api.repository.UserRepository;
import com.develop.dental_api.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AppointmentRepository appointmentRepository;
    private final PaymentRepository paymentRepository;
    private final ProfileRepository profileRepository;
    private final UserMapper userMapper;
    private final ProfileMapper profileMapper;
    private final AppointmentMapper appointmentMapper;
    private final PaymentMapper paymentMapper;

    @Override
    public List<UserAppointmentDTO> getAppointments(Integer userId) {
        return appointmentRepository.findByPatient_UserId(userId).stream()
                .map(appointmentMapper::toUserAppointmentDTO)
                .toList();
    }

    @Override
    public List<UserPaymentDTO> getPayments(Integer userId) {
        return paymentRepository.findByAppointment_Patient_UserId(userId).stream()
                .map(paymentMapper::toUserPaymentDTO)
                .toList();
    }

    @Override
    public UserProfileDTO getProfile(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Profile profile = profileRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado"));
        return userMapper.toUserProfileDTO(user, profile);
    }

    @Override
    public MessageResponseDTO updateProfile(Integer userId, UpdateProfileDTO dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Profile profile = profileRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado"));
        profileMapper.updateProfileFromDto(dto, profile);
        profileRepository.save(profile);
        return new MessageResponseDTO("Perfil actualizado");
    }
}
