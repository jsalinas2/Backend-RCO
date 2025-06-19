package com.develop.dental_api.service;

import com.develop.dental_api.model.dto.PaymentDetailDTO;
import com.develop.dental_api.model.dto.PaymentRequestDTO;
import com.develop.dental_api.model.dto.PaymentResponseDTO;
import com.develop.dental_api.model.dto.UserPaymentHistoryDTO;

import java.util.List;

public interface PaymentService {

    String registerPayment(Integer appointmentId) throws Exception;

    void updatePaymentStatus(String paymentId) throws Exception;
    PaymentDetailDTO getPaymentDetail(Integer paymentId);
    List<UserPaymentHistoryDTO> getUserPayments(Integer userId);
}