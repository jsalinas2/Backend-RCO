package com.develop.dental_api.model.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class CreateServiceDTO {
    private String name;
    private String description;
    private BigDecimal price;
    private Boolean isRecurring;
}
