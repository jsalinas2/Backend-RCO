package com.develop.dental_api.repository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.develop.dental_api.model.entity.Availability;
import com.develop.dental_api.model.entity.User;

@Repository
public interface AvailabilityRepository extends JpaRepository<Availability, Integer> {
    List<Availability> findAllByDentistAndDayOfWeek(User dentist, DayOfWeek dayOfWeek);
}