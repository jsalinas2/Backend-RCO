package com.develop.dental_api.service.implement;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.develop.dental_api.model.dto.AvailabilityDTO;
import com.develop.dental_api.model.dto.CreateAvailabilityDTO;
import com.develop.dental_api.model.dto.UpdateAvailabilityDTO;
import com.develop.dental_api.model.entity.Availability;
import com.develop.dental_api.model.entity.User;
import com.develop.dental_api.model.mapper.AvailabilityMapper;
import com.develop.dental_api.repository.AvailabilityRepository;
import com.develop.dental_api.repository.UserRepository;
import com.develop.dental_api.service.AvailabilityService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AvailabilityServiceImpl implements AvailabilityService {

    private final AvailabilityRepository availabilityRepository;
    private final UserRepository userRepository;
    private final AvailabilityMapper availabilityMapper;

    @Override
    public List<AvailabilityDTO> getDentistAvailabilities(Integer dentistId) {
        User dentist = userRepository.findById(dentistId)
                .orElseThrow(() -> new RuntimeException("Dentista no encontrado"));
        return availabilityRepository.findAllByDentist(dentist).stream()
                .map(availabilityMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public AvailabilityDTO addAvailability(CreateAvailabilityDTO dto) {
        User dentist = userRepository.findById(dto.getDentistId())
                .orElseThrow(() -> new RuntimeException("Dentista no encontrado"));

        // Verificar solapamiento
        List<Availability> existing = availabilityRepository.findAllByDentist(dentist).stream()
                .filter(a -> a.getDayOfWeek() == dto.getDayOfWeek())
                .collect(Collectors.toList());

        for (Availability a : existing) {
            // Si los rangos se solapan
            boolean overlap = !dto.getEndTime().isBefore(a.getStartTime()) && !dto.getStartTime().isAfter(a.getEndTime());
            if (overlap) {
                throw new RuntimeException("El rango horario se cruza con otra disponibilidad existente.");
            }
        }

        Availability availability = availabilityMapper.toEntity(dto, dentist);
        return availabilityMapper.toDTO(availabilityRepository.save(availability));
    }

    @Override
    @Transactional
    public void deleteAvailability(Integer availabilityId) {
        availabilityRepository.deleteById(availabilityId);
    }

    @Override
    @Transactional
    public AvailabilityDTO updateAvailability(Integer availabilityId, UpdateAvailabilityDTO dto) {
        Availability availability = availabilityRepository.findById(availabilityId)
                .orElseThrow(() -> new RuntimeException("Disponibilidad no encontrada"));
        User dentist = userRepository.findById(dto.getDentistId())
                .orElseThrow(() -> new RuntimeException("Dentista no encontrado"));

        // Validar solapamiento con otras disponibilidades del mismo dentista y día (excluyendo la actual)
        List<Availability> existing = availabilityRepository.findAllByDentist(dentist).stream()
                .filter(a -> a.getDayOfWeek() == dto.getDayOfWeek() && !a.getId().equals(availabilityId))
                .collect(Collectors.toList());

        for (Availability a : existing) {
            boolean overlap = !dto.getEndTime().isBefore(a.getStartTime()) && !dto.getStartTime().isAfter(a.getEndTime());
            if (overlap) {
                throw new RuntimeException("El rango horario se cruza con otra disponibilidad existente.");
            }
        }

        availability.setDentist(dentist);
        availability.setDayOfWeek(dto.getDayOfWeek());
        availability.setStartTime(dto.getStartTime());
        availability.setEndTime(dto.getEndTime());

        return availabilityMapper.toDTO(availabilityRepository.save(availability));
    }
}
