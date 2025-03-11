package com.example.demo.Model.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "info_familia")

public class Family {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

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

    public Family(String nombre_completo_hijo_1,
                  String tipo_documento_hijo_1,
                  String numero_documento_hijo_1,
                  String fecha_nacimiento_hijo_1,
                  String sexo_hijo_1,
                  String tipo_documento_hijo_2,
                  String numero_documento_hijo_2,
                  String fecha_nacimiento_hijo_2,
                  String sexo_hijo_2,
                  String nombre_completo_hijo_2

                  //String id_key
    )
    {
        this.nombre_completo_hijo_1 = nombre_completo_hijo_1;
        this.tipo_documento_hijo_1 = tipo_documento_hijo_1;
        this.numero_documento_hijo_1 = numero_documento_hijo_1;
        this.fecha_nacimiento_hijo_1 = fecha_nacimiento_hijo_1;
        this.sexo_hijo_1 = sexo_hijo_1;
        this.tipo_documento_hijo_2 = tipo_documento_hijo_2;
        this.numero_documento_hijo_2 = numero_documento_hijo_2;
        this.fecha_nacimiento_hijo_2 = fecha_nacimiento_hijo_2;
        this.sexo_hijo_2 = sexo_hijo_2;
        this.nombre_completo_hijo_2 = nombre_completo_hijo_2;

        //this.id_key = id_key
    }
}
