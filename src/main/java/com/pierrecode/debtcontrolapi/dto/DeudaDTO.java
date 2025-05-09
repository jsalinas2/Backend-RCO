package com.pierrecode.debtcontrolapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeudaDTO {

    private int id;

    @NotNull(message = "El monto es obligatorio")
    @Positive(message = "El monto debe ser un valor positivo")
    private double monto;

    @NotBlank(message = "La descripcion es obligatorio")
    @Size(min = 5, max = 200, message = "La descripcion debe tener entre 5 y 200 caracteres")
    private String descripcion;

    @NotNull(message = "La fecha de vencimiento es obligatorio")
    private LocalDate fechaVenc;

    private boolean estadoPago;

    @NotNull(message = "La categoria es obligatoria")
    private int categoriaId;

    private String categoriaNombre;


}
