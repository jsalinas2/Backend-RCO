package com.develop.dental_api.model.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class UserPaymentHistoryDTO {
    private Integer paymentId;
    private LocalDateTime paymentDate;
    private String serviceName;
    private BigDecimal amount;
    private String status;
}