package com.develop.dental_api.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentRequestDTO {
    private LocalDateTime appointmentDate;
    private Integer serviceId;
    private String reason;
    private Integer patientId;
    private Integer dentistId;
}