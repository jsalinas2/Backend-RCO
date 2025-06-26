package com.develop.dental_api.model.dto;

import java.time.DayOfWeek;
import java.time.LocalTime;
import lombok.Data;

@Data
public class CreateAvailabilityDTO {
    private Integer dentistId;
    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
}
