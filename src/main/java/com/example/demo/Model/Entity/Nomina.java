package com.example.demo.Model.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "info_nomina")
public class Nomina {

    @Id
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

    public Nomina(String tipo_contrato,
                  String cargo,
                  String jefe_a_cargo,
                  String c_costo_especif,
                  String email_corporativo,
                  String id_key,
                  String fecha_ingreso,
                  String ubicacion_laboral,
                  String eps,
                  String fondo_de_cesantias,
                  String fondo_de_pensiones,
                  String caja_de_compensacion,
                  String salario,
                  String aux_transporte,
                  String aux_comunicacion,
                  String aux_rodamiento,
                  String aux_digital,
                  String banco,
                  String numero_cuenta)
    {
        this.tipo_contrato = tipo_contrato;
        this.cargo = cargo;
        this.jefe_a_cargo = jefe_a_cargo;
        this.c_costo_especif = c_costo_especif;
        this.email_corporativo = email_corporativo;
        this.id_key = id_key;
        this.fecha_ingreso = fecha_ingreso;
        this.ubicacion_laboral = ubicacion_laboral;
        this.eps = eps;
        this.fondo_de_cesantias = fondo_de_cesantias;
        this.fondo_de_pensiones = fondo_de_pensiones;
        this.caja_de_compensacion = caja_de_compensacion;
        this.salario = salario;
        this.aux_transporte= aux_transporte;
        this.aux_comunicacion = aux_comunicacion;
        this.aux_rodamiento = aux_rodamiento;
        this.aux_digital = aux_digital;
        this.banco = banco;
        this.numero_cuenta = numero_cuenta;

    }

}
