package com.example.demo.Dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class NominaDto {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipo_contrato;
    private String cargo;
    private String jefe_a_cargo;
    private String c_costo_especif;
    private String email_corporativo;
    private String id_key;
    private String fecha_ingreso;
    private String ubicacion_laboral;
    private String eps;
    private String fondo_de_cesantias;
    private String fondo_de_pensiones;
    private String caja_de_compensacion;
    private String salario;
    private String aux_transporte;
    private String aux_comunicacion;
    private String aux_rodamiento;
    private String aux_digital;
    private String banco;
    private String numero_cuenta;


}
