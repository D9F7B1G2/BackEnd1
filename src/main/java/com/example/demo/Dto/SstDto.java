package com.example.demo.Dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class SstDto {

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

}
