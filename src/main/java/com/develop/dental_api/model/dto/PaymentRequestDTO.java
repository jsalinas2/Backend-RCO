package com.develop.dental_api.model.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentRequestDTO {
    private Integer appointmentId;
    private BigDecimal amount;
    private String paymentMethod;
}