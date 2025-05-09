package com.develop.dental_api.service;

import com.develop.dental_api.model.dto.PaymentDetailDTO;
import com.develop.dental_api.model.dto.PaymentRequestDTO;
import com.develop.dental_api.model.dto.PaymentResponseDTO;

public interface PaymentService {

    String registerPayment(Integer appointmentId) throws Exception;

    void updatePaymentStatus(String paymentId) throws Exception;
    PaymentDetailDTO getPaymentDetail(Integer paymentId);
}