package com.develop.dental_api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.develop.dental_api.model.entity.Appointment;
import com.develop.dental_api.model.entity.Payment;
import com.develop.dental_api.model.entity.User;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    List<Payment> findByAppointment(Appointment appointment);
    List<Payment> findByAppointment_Patient(User user);
    List<Payment> findByAppointment_Patient_UserId(Integer userId);
    Optional<Payment> findByMercadoPagoId(String mercadoPagoId);
}
