package com.develop.dental_api.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.develop.dental_api.model.dto.PaymentDetailDTO;
import com.develop.dental_api.model.dto.PaymentRequestDTO;
import com.develop.dental_api.model.dto.UserPaymentDTO;
import com.develop.dental_api.model.entity.Appointment;
import com.develop.dental_api.model.entity.Payment;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    
    @Mapping(target = "appointment", source = "appointment")
    Payment toPaymentEntity(PaymentRequestDTO dto, Appointment appointment);

    @Mapping(target = "paymentId", source = "paymentId")
    @Mapping(target = "method", source = "paymentMethod")
    PaymentDetailDTO toPaymentDetailDTO(Payment payment);

    @Mapping(target = "paymentId", source = "paymentId")
    @Mapping(target = "appointmentId", source = "appointment.appointmentId")
    UserPaymentDTO toUserPaymentDTO(Payment payment);
}