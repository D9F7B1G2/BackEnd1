package com.example.demo.Model.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "conductores")

public class Driver {
    @Id
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

    public Driver(String tipo_licencia,
                  String lugar_expedicion,
                  String fecha_expedicion,
                  String fecha_vencimiento,
                  String años_experiencia,
                  String accidentes_viales,
                  String tipo_accidentes_viales,
                  String cantidad_accidentes,
                  String medio_de_transporte_propio,
                  String cantidad_tipos_transporte,
                  String tipos_de_transporte
                  //String id_key

    )
    {
    this.tipo_licencia = tipo_licencia;
    this.lugar_expedicion = lugar_expedicion;
    this.fecha_expedicion = fecha_expedicion;
    this.fecha_vencimiento = fecha_vencimiento;
    this.años_experiencia = años_experiencia;
    this.accidentes_viales = accidentes_viales;
    this.tipo_accidentes_viales = tipo_accidentes_viales;
    this.cantidad_accidentes = cantidad_accidentes;
    this.medio_de_transporte_propio =medio_de_transporte_propio;
    this.cantidad_tipos_transporte = cantidad_tipos_transporte;
    this.tipos_de_transporte = tipos_de_transporte;
    //this.id_key = id_key
    }
}

