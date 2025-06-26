package com.develop.dental_api.model.mapper;

import com.develop.dental_api.model.dto.AppointmentAgendaDTO;
import com.develop.dental_api.model.dto.AppointmentRequestDTO;
import com.develop.dental_api.model.dto.AppointmentResponseDTO;
import com.develop.dental_api.model.dto.UserAppointmentDTO;
import com.develop.dental_api.model.entity.Appointment;
import com.develop.dental_api.model.entity.ServiceEntity;
import com.develop.dental_api.model.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-26T05:07:01-0500",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.4 (Eclipse Adoptium)"
)
@Component
public class AppointmentMapperImpl implements AppointmentMapper {

    @Override
    public Appointment toAppointmentEntity(AppointmentRequestDTO dto, User patient, User dentist, ServiceEntity service) {
        if ( dto == null && patient == null && dentist == null && service == null ) {
            return null;
        }

        Appointment appointment = new Appointment();

        if ( dto != null ) {
            appointment.setAppointmentDate( dto.getAppointmentDate() );
            appointment.setReason( dto.getReason() );
        }
        appointment.setPatient( patient );
        appointment.setDentist( dentist );
        appointment.setService( service );
        appointment.setCreatedAt( java.time.LocalDateTime.now() );

        return appointment;
    }

    @Override
    public AppointmentAgendaDTO toAgendaDTO(Appointment appointment) {
        if ( appointment == null ) {
            return null;
        }

        AppointmentAgendaDTO appointmentAgendaDTO = new AppointmentAgendaDTO();

        appointmentAgendaDTO.setAppointmentId( appointment.getAppointmentId() );
        appointmentAgendaDTO.setAppointmentDate( appointment.getAppointmentDate() );
        appointmentAgendaDTO.setStatus( appointment.getStatus() );

        appointmentAgendaDTO.setService( appointment.getService().getName() );
        appointmentAgendaDTO.setPatientName( fullName(appointment.getPatient()) );
        appointmentAgendaDTO.setPatientDni( appointment.getPatient().getProfile().getDni() );
        appointmentAgendaDTO.setDentistName( fullName(appointment.getDentist()) );

        return appointmentAgendaDTO;
    }

    @Override
    public UserAppointmentDTO toUserAppointmentDTO(Appointment appointment) {
        if ( appointment == null ) {
            return null;
        }

        UserAppointmentDTO userAppointmentDTO = new UserAppointmentDTO();

        userAppointmentDTO.setAppointmentId( appointment.getAppointmentId() );
        userAppointmentDTO.setAppointmentDate( appointment.getAppointmentDate() );
        userAppointmentDTO.setStatus( appointment.getStatus() );

        userAppointmentDTO.setService( appointment.getService().getName() );
        userAppointmentDTO.setDentistName( fullName(appointment.getDentist()) );

        return userAppointmentDTO;
    }

    @Override
    public AppointmentResponseDTO toAppointmentResponseDTO(Appointment appointment) {
        if ( appointment == null ) {
            return null;
        }

        Long appointmentId = null;

        if ( appointment.getAppointmentId() != null ) {
            appointmentId = appointment.getAppointmentId().longValue();
        }

        String message = "Cita agendada";

        AppointmentResponseDTO appointmentResponseDTO = new AppointmentResponseDTO( message, appointmentId );

        return appointmentResponseDTO;
    }
}
