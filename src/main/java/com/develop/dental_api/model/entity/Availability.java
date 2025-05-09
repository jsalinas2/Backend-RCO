package com.develop.dental_api.model.entity;

import java.time.DayOfWeek;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Availability {

    @Id @GeneratedValue
    private Integer id;

    @ManyToOne
    private User dentist;

    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek; // ej: MONDAY

    private LocalTime startTime; // ej: 09:00
    private LocalTime endTime;   // ej: 17:00
}
