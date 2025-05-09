package com.pierrecode.debtcontrolapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {

    private int id;

    @NotBlank(message = "El nombre es obligario")
    @Size(max = 100, message = "El nombre debe tener 100 caracteres o menos")
    private String nombre;

    @NotBlank(message = "el email es obligatorio")
    @Size(message = "Debe ser un correo electronico valido")
    private String email;



}
