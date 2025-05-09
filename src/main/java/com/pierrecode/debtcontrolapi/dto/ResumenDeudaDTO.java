package com.pierrecode.debtcontrolapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResumenDeudaDTO {

    private double totalPendiente;
    private double totalVencido;
    private double totalProximo;
    private int cantidadPendientes;
    private int cantidadVencidas;
    private int cantidadProximas;
    private List<Map<String, Object>> resumenPorCategoria;


}
