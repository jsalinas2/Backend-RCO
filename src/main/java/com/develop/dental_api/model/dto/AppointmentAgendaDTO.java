package com.develop.dental_api.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentAgendaDTO {
    private Integer appointmentId;
    private LocalDateTime appointmentDate;
    private String service;
    private String status;
    private String patientName;
    private String dentistName;
}