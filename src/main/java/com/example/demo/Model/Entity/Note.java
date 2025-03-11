package com.example.demo.Model.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "llamados_de_atencion")

public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    private String tipo;
    private String asunto;
    private String fecha;

    //private String id_key;

    public Note(String tipo,
                String asunto,
                String fecha

                //String id_key
    )
    {
        this.tipo = tipo ;
        this.asunto = asunto ;
        this.fecha = fecha ;

        //this.id_key = id_key
    }
}
