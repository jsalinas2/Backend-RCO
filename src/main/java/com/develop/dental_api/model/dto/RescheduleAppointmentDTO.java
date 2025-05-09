package com.develop.dental_api.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RescheduleAppointmentDTO {
    private LocalDateTime newDate;
}