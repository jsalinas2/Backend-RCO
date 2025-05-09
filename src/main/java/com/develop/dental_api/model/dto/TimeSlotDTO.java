package com.develop.dental_api.model.dto;

import java.time.LocalTime;

import lombok.Data;

@Data
public class TimeSlotDTO {
    private String hora;
    private boolean disponible;

    public TimeSlotDTO(LocalTime hora, boolean disponible) {
        this.hora = hora.toString();
        this.disponible = disponible;
    }
}
