package com.develop.dental_api.service.implement;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.develop.dental_api.model.dto.AppointmentAgendaDTO;
import com.develop.dental_api.model.dto.AppointmentRequestDTO;
import com.develop.dental_api.model.dto.AppointmentResponseDTO;
import com.develop.dental_api.model.dto.MessageResponseDTO;
import com.develop.dental_api.model.dto.TimeSlotDTO;
import com.develop.dental_api.model.entity.Appointment;
import com.develop.dental_api.model.entity.Availability;
import com.develop.dental_api.model.entity.User;
import com.develop.dental_api.model.mapper.AppointmentMapper;
import com.develop.dental_api.repository.AppointmentRepository;
import com.develop.dental_api.repository.AvailabilityRepository;
import com.develop.dental_api.repository.ServiceRepository;
import com.develop.dental_api.repository.UserRepository;
import com.develop.dental_api.service.AppointmentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final AppointmentMapper appointmentMapper;
    private final ServiceRepository serviceRepository;
    private final AvailabilityRepository availabilityRepository;

    @Override
    public AppointmentResponseDTO createAppointment(AppointmentRequestDTO dto) {
    User patient = userRepository.findById(dto.getPatientId())
        .orElseThrow(() -> new IllegalArgumentException("Paciente no encontrado"));

    User dentist = userRepository.findById(dto.getDentistId())
        .orElseThrow(() -> new IllegalArgumentException("Dentista no encontrado"));

    com.develop.dental_api.model.entity.Service service = serviceRepository.findById(dto.getServiceId())
        .orElseThrow(() -> new IllegalArgumentException("Servicio no encontrado"));

    LocalDateTime startDateTime = dto.getAppointmentDate();
    LocalTime appointmentTime = startDateTime.toLocalTime();
    LocalDate appointmentDate = startDateTime.toLocalDate();
    LocalDateTime endDateTime = startDateTime.plusHours(1);

    DayOfWeek dayOfWeek = appointmentDate.getDayOfWeek();

    List<Availability> disponibilidades = availabilityRepository.findAllByDentistAndDayOfWeek(dentist, dayOfWeek);

    if (disponibilidades.isEmpty()) {
        throw new IllegalArgumentException("El dentista no trabaja ese día.");
    }
    
    boolean dentroDeRango = disponibilidades.stream().anyMatch(dispo ->
        !appointmentTime.isBefore(dispo.getStartTime()) &&
        !appointmentTime.plusHours(1).isAfter(dispo.getEndTime())
    );
    
    if (!dentroDeRango) {
        throw new IllegalArgumentException("El horario no está dentro de ningún rango disponible del dentista.");
    }
    

    List<Appointment> overlappingAppointments = appointmentRepository
        .findByDentistAndAppointmentDateBetween(dentist, startDateTime, endDateTime.minusNanos(1));

    if (!overlappingAppointments.isEmpty()) {
        throw new IllegalArgumentException("Este horario ya está reservado.");
    }

    Appointment appointment = appointmentMapper.toAppointmentEntity(dto, patient, dentist, service);
    appointment.setStatus("Scheduled");
    appointment.setAppointmentDate(startDateTime);

    appointment = appointmentRepository.save(appointment);
    return appointmentMapper.toAppointmentResponseDTO(appointment);
    }

    

    @Override
    public List<TimeSlotDTO> getAvailableSlots(User dentist, LocalDate fecha) {
        DayOfWeek dia = fecha.getDayOfWeek();
    
        // 1. Obtener todas las disponibilidades del dentista ese día
        List<Availability> disponibilidades = availabilityRepository.findAllByDentistAndDayOfWeek(dentist, dia);
    
        if (disponibilidades.isEmpty()) {
            return Collections.emptyList(); // No trabaja ese día
        }
    
        List<TimeSlotDTO> horariosDisponibles = new ArrayList<>();
    
        // 2. Obtener todas las citas del dentista en ese día en una sola consulta
        LocalDateTime diaInicio = fecha.atStartOfDay();
        LocalDateTime diaFin = fecha.atTime(LocalTime.MAX);
    
        List<Appointment> citasDelDia = appointmentRepository
            .findByDentistAndAppointmentDateBetween(dentist, diaInicio, diaFin);
    
        // 3. Para cada disponibilidad, generar bloques de 1 hora y verificar disponibilidad
        for (Availability disponibilidad : disponibilidades) {
            LocalTime horaActual = disponibilidad.getStartTime();
            LocalTime horaFin = disponibilidad.getEndTime();
    
            while (!horaActual.plusHours(1).isAfter(horaFin)) {
                LocalDateTime inicio = fecha.atTime(horaActual);
                LocalDateTime fin = inicio.plusHours(1);
    
                // Verificar en memoria si ya hay una cita en este bloque
                boolean reservado = citasDelDia.stream().anyMatch(cita ->
                    !cita.getAppointmentDate().isBefore(inicio) &&
                    cita.getAppointmentDate().isBefore(fin)
                );
    
                horariosDisponibles.add(new TimeSlotDTO(horaActual, !reservado));
                horaActual = horaActual.plusHours(1);
            }
        }
    
        return horariosDisponibles;
    }
    
    


    @Override
    public List<AppointmentAgendaDTO> getAgendaForUser(LocalDate date, Integer userId) {
        User dentist = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX); // 23:59:59.999999999
    
        List<Appointment> appointments = appointmentRepository
                .findByDentistAndAppointmentDateBetween(dentist, startOfDay, endOfDay);
    
        return appointments.stream()
                .map(appointmentMapper::toAgendaDTO)
                .toList();
    }
    

    @Override
    public MessageResponseDTO reschedule(Integer appointmentId, LocalDateTime newDate) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));

        appointment.setAppointmentDate(newDate);
        appointmentRepository.save(appointment);
        return new MessageResponseDTO("Cita reprogramada");
    }
}
