package com.develop.dental_api.service.implement;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.develop.dental_api.model.dto.ServiceDTO;
import com.develop.dental_api.model.dto.CreateServiceDTO;
import com.develop.dental_api.model.dto.UpdateServiceDTO;
import com.develop.dental_api.model.entity.ServiceEntity;
import com.develop.dental_api.model.mapper.ServiceMapper;
import com.develop.dental_api.repository.ServiceRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServiceServiceImpl implements com.develop.dental_api.service.ServiceService {

    private final ServiceRepository serviceRepository;
    private final ServiceMapper serviceMapper;

    @Override
    public List<ServiceDTO> getAll() {
        return serviceRepository.findAllByActiveTrue()
                .stream()
                .map(serviceMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ServiceDTO getById(Integer id) {
        ServiceEntity service = serviceRepository.findById(id)
                .filter(ServiceEntity::getActive)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado"));
        return serviceMapper.toDTO(service);
    }

    @Override
    @Transactional
    public ServiceDTO create(CreateServiceDTO dto) {
        ServiceEntity service = serviceMapper.toEntity(dto);
        service.setActive(true);
        System.out.println("Creating service: " + service.getServiceId());
        return serviceMapper.toDTO(serviceRepository.save(service));
    }

    @Override
    @Transactional
    public ServiceDTO update(Integer id, UpdateServiceDTO dto) {
        ServiceEntity service = serviceRepository.findById(id)
                .filter(ServiceEntity::getActive)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado"));
        serviceMapper.updateEntityFromDto(dto, service);
        return serviceMapper.toDTO(serviceRepository.save(service));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        ServiceEntity service = serviceRepository.findById(id)
                .filter(ServiceEntity::getActive)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado"));
        service.setActive(false);
        serviceRepository.save(service);
    }
}
