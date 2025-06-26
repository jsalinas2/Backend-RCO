package com.develop.dental_api.model.mapper;

import com.develop.dental_api.model.dto.PaymentDetailDTO;
import com.develop.dental_api.model.dto.PaymentRequestDTO;
import com.develop.dental_api.model.dto.UserPaymentDTO;
import com.develop.dental_api.model.entity.Appointment;
import com.develop.dental_api.model.entity.Payment;
import com.develop.dental_api.model.entity.ServiceEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-26T05:07:02-0500",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.4 (Eclipse Adoptium)"
)
@Component
public class PaymentMapperImpl implements PaymentMapper {

    @Override
    public Payment toPaymentEntity(PaymentRequestDTO dto, Appointment appointment) {
        if ( dto == null && appointment == null ) {
            return null;
        }

        Payment payment = new Payment();

        if ( dto != null ) {
            payment.setAmount( dto.getAmount() );
            payment.setPaymentMethod( dto.getPaymentMethod() );
        }
        if ( appointment != null ) {
            payment.setAppointment( appointment );
            payment.setStatus( appointment.getStatus() );
        }

        return payment;
    }

    @Override
    public PaymentDetailDTO toPaymentDetailDTO(Payment payment) {
        if ( payment == null ) {
            return null;
        }

        PaymentDetailDTO paymentDetailDTO = new PaymentDetailDTO();

        paymentDetailDTO.setPaymentId( payment.getPaymentId() );
        paymentDetailDTO.setMethod( payment.getPaymentMethod() );
        paymentDetailDTO.setAmount( payment.getAmount() );
        paymentDetailDTO.setPaymentDate( payment.getPaymentDate() );
        paymentDetailDTO.setStatus( payment.getStatus() );

        return paymentDetailDTO;
    }

    @Override
    public UserPaymentDTO toUserPaymentDTO(Payment payment) {
        if ( payment == null ) {
            return null;
        }

        UserPaymentDTO userPaymentDTO = new UserPaymentDTO();

        userPaymentDTO.setPaymentId( payment.getPaymentId() );
        userPaymentDTO.setAppointmentId( paymentAppointmentAppointmentId( payment ) );
        userPaymentDTO.setServiceName( paymentAppointmentServiceName( payment ) );
        userPaymentDTO.setAmount( payment.getAmount() );
        userPaymentDTO.setPaymentDate( payment.getPaymentDate() );
        userPaymentDTO.setStatus( payment.getStatus() );

        return userPaymentDTO;
    }

    private Integer paymentAppointmentAppointmentId(Payment payment) {
        Appointment appointment = payment.getAppointment();
        if ( appointment == null ) {
            return null;
        }
        return appointment.getAppointmentId();
    }

    private String paymentAppointmentServiceName(Payment payment) {
        Appointment appointment = payment.getAppointment();
        if ( appointment == null ) {
            return null;
        }
        ServiceEntity service = appointment.getService();
        if ( service == null ) {
            return null;
        }
        return service.getName();
    }
}
