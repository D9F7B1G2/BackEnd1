package com.example.demo.Model.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sst")
public class Sst {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String requiere_epp;
    private String talla_camisa;
    private String talla_pantalon;
    private String talla_botas;
    private String talla_guantes;
    private String fecha_entrega_botas;
    private String fecha_entrega_pantalon;
    private String fecha_entrega_camisa;
    private String fecha_entrega_guantes;
    private String requiere_equipo_oficina;
    private String computador;
    private String teclado;
    private String mouse;
    private String fecha_entrega_computador;
    private String fecha_entrega_teclado;
    private String fecha_entrega_mouse;
    private String base_computador;
    private String fecha_entrega_base_computador;
    private String requiere_celular;
    private String marca_computador;
    private String serial_computador;
    private String marca_celular;
    private String serial_celular;
    private String detalles_fisicos_computador;
    private String detalles_fisicos_celular;


    public Sst(String requiere_epp,
               String talla_camisa,
               String talla_pantalon,
               String talla_botas,
               String talla_guantes,
               String fecha_entrega_botas,
               String fecha_entrega_pantalon,
               String fecha_entrega_camisa,
               String fecha_entrega_guantes,
               String requiere_equipo_oficina,
               String computador,
               String teclado,
               String mouse,
               String fecha_entrega_computador,
               String fecha_entrega_teclado,
               String fecha_entrega_mouse,
               String base_computador,
               String fecha_entrega_base_computador,
               String requiere_celular,
               String marca_computador,
               String serial_computador,
               String marca_celular,
               String serial_celular,
               String detalles_fisicos_computador,
               String detalles_fisicos_celular

        )
    {
        this.requiere_epp = requiere_epp;
        this.talla_camisa = talla_camisa;
        this.talla_pantalon = talla_pantalon;
        this.talla_botas = talla_botas;
        this.talla_guantes = talla_guantes;
        this.fecha_entrega_botas = fecha_entrega_botas;
        this.fecha_entrega_pantalon = fecha_entrega_pantalon;
        this.fecha_entrega_camisa = fecha_entrega_camisa;
        this.fecha_entrega_guantes = fecha_entrega_guantes;
        this.requiere_equipo_oficina = requiere_equipo_oficina;
        this.computador = computador;
        this.teclado = teclado;
        this.mouse = mouse;
        this.fecha_entrega_computador = fecha_entrega_computador;
        this.fecha_entrega_teclado = fecha_entrega_teclado;
        this.fecha_entrega_mouse = fecha_entrega_mouse;
        this.base_computador = base_computador;
        this.fecha_entrega_base_computador = fecha_entrega_base_computador;
        this.requiere_celular = requiere_celular;
        this.marca_computador = marca_computador;
        this.serial_computador = serial_computador;
        this.marca_celular = marca_celular;
        this.serial_celular = serial_celular;
        this.detalles_fisicos_computador = detalles_fisicos_computador;
        this.detalles_fisicos_celular = detalles_fisicos_celular;


    }


}
