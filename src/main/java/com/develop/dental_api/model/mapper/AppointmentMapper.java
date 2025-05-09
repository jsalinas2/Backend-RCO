package com.develop.dental_api.model.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.develop.dental_api.model.dto.AppointmentAgendaDTO;
import com.develop.dental_api.model.dto.AppointmentRequestDTO;
import com.develop.dental_api.model.dto.AppointmentResponseDTO;
import com.develop.dental_api.model.dto.UserAppointmentDTO;
import com.develop.dental_api.model.entity.Appointment;
import com.develop.dental_api.model.entity.Service;
import com.develop.dental_api.model.entity.User;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {

    @Mapping(target = "appointmentId", ignore = true)
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    Appointment toAppointmentEntity(AppointmentRequestDTO dto, User patient, User dentist, Service service);

    @Mapping(target = "service", expression = "java(appointment.getService().getName())")
    @Mapping(target = "patientName", expression = "java(fullName(appointment.getPatient()))")
    @Mapping(target = "dentistName", expression = "java(fullName(appointment.getDentist()))")
    AppointmentAgendaDTO toAgendaDTO(Appointment appointment);
    
    default String fullName(User user) {
        if (user == null || user.getProfile() == null) return null;
        return user.getProfile().getFirstName() + " " + user.getProfile().getLastName();
    }
    
    @Mapping(target = "service", expression = "java(appointment.getService().getName())")
    @Mapping(target = "dentistName", expression = "java(fullName(appointment.getDentist()))")
    UserAppointmentDTO toUserAppointmentDTO(Appointment appointment);

    @Mapping(target = "appointmentId", source = "appointment.appointmentId")
    @Mapping(target = "message", constant = "Cita agendada")
    AppointmentResponseDTO toAppointmentResponseDTO(Appointment appointment);
}