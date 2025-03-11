package com.example.demo.Dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class DriverDto {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    private String tipo_licencia;
    private String lugar_expedicion;
    private String fecha_expedicion;
    private String fecha_vencimiento;
    private String años_experiencia;
    private String accidentes_viales;
    private String tipo_accidentes_viales;
    private String cantidad_accidentes;
    private String medio_de_transporte_propio;
    private String cantidad_tipos_transporte;
    private String tipos_de_transporte;
    //private String id_key
}
