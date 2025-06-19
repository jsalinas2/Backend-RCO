package com.develop.dental_api.model.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TreatmentDoneDTO {
    private Integer treatmentsId;
    private String serviceName;
    private LocalDate treatmentDate;
    private String observations;
    private String status;
    private LocalDateTime createdAt;
}