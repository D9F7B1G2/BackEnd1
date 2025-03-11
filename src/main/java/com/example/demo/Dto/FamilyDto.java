package com.example.demo.Dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class FamilyDto {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre_completo_hijo_1;
    private String tipo_documento_hijo_1;
    private String numero_documento_hijo_1;
    private String fecha_nacimiento_hijo_1;
    private String sexo_hijo_1;
    private String tipo_documento_hijo_2;
    private String numero_documento_hijo_2;
    private String fecha_nacimiento_hijo_2;
    private String sexo_hijo_2;
    private String nombre_completo_hijo_2;
    //private String id_key;

}
