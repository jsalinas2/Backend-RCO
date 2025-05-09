package com.develop.dental_api.model.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PaymentDetailDTO {
    private Integer paymentId;
    private BigDecimal amount;
    private LocalDateTime paymentDate;
    private String method;
    private String status;
}