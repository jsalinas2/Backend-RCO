package com.develop.dental_api.service;

import java.util.List;
import com.develop.dental_api.model.dto.ServiceDTO;
import com.develop.dental_api.model.dto.CreateServiceDTO;
import com.develop.dental_api.model.dto.UpdateServiceDTO;

public interface ServiceService {
    List<ServiceDTO> getAll();
    ServiceDTO getById(Integer id);
    ServiceDTO create(CreateServiceDTO dto);
    ServiceDTO update(Integer id, UpdateServiceDTO dto);
    void delete(Integer id);
}
