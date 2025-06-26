package com.develop.dental_api.service;

import java.util.List;
import com.develop.dental_api.model.dto.AvailabilityDTO;
import com.develop.dental_api.model.dto.CreateAvailabilityDTO;
import com.develop.dental_api.model.dto.UpdateAvailabilityDTO;

public interface AvailabilityService {
    List<AvailabilityDTO> getDentistAvailabilities(Integer dentistId);
    AvailabilityDTO addAvailability(CreateAvailabilityDTO dto);
    void deleteAvailability(Integer availabilityId);
    AvailabilityDTO updateAvailability(Integer availabilityId, UpdateAvailabilityDTO dto);
}
