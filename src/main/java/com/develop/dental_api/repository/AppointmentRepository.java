package com.develop.dental_api.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.develop.dental_api.model.entity.Appointment;
import com.develop.dental_api.model.entity.User;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    List<Appointment> findByPatient(User patient);
    List<Appointment> findByDentist(User dentist);
    List<Appointment> findByPatient_UserId(Integer id);
    List<Appointment> findByDentistAndAppointmentDateBetween(User dentist, LocalDateTime start, LocalDateTime end);
    boolean existsByDentistAndAppointmentDateBetween(User dentist, LocalDateTime start, LocalDateTime end);

}