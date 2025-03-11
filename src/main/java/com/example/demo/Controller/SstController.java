package com.example.demo.Controller;

import com.example.demo.Dto.SstDto;
import com.example.demo.Model.Entity.Sst;
import com.example.demo.Services.SstServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("x-cargo")
@RestController
public class SstController {

    @Autowired
    SstServices sstServices;
    @PostMapping("/sst")

    public ResponseEntity<?> sstSst(@RequestBody SstDto sstDto) {

        Sst sst = new Sst(
                sstDto.getRequiere_epp(),
                sstDto.getTalla_camisa(),
                sstDto.getTalla_pantalon(),
                sstDto.getTalla_botas(),
                sstDto.getTalla_guantes(),
                sstDto.getFecha_entrega_botas(),
                sstDto.getFecha_entrega_pantalon(),
                sstDto.getFecha_entrega_camisa(),
                sstDto.getFecha_entrega_guantes(),
                sstDto.getRequiere_equipo_oficina(),
                sstDto.getComputador(),
                sstDto.getTeclado(),
                sstDto.getMouse(),
                sstDto.getFecha_entrega_computador(),
                sstDto.getFecha_entrega_teclado(),
                sstDto.getFecha_entrega_mouse(),
                sstDto.getBase_computador(),
                sstDto.getFecha_entrega_base_computador(),
                sstDto.getRequiere_celular(),
                sstDto.getMarca_computador(),
                sstDto.getSerial_computador(),
                sstDto.getMarca_celular(),
                sstDto.getSerial_celular(),
                sstDto.getDetalles_fisicos_computador(),
                sstDto.getDetalles_fisicos_celular()


        );

        sstServices.savessts(sst);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
