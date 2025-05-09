package com.develop.dental_api.model.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class UserPaymentDTO {
    private Integer paymentId;
    private Integer appointmentId;
    private BigDecimal amount;
    private LocalDateTime paymentDate;
    private String status;
}