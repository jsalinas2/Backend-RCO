package com.develop.dental_api.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserAppointmentDTO {
    private Integer appointmentId;
    private LocalDateTime appointmentDate;
    private String service;
    private String status;
    private String dentistName;
}