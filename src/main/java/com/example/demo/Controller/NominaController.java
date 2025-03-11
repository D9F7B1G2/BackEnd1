package com.example.demo.Controller;

import com.example.demo.Dto.NominaDto;
import com.example.demo.Model.Entity.Nomina;
import com.example.demo.Services.NominaServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("x-cargo")
@RestController
public class NominaController {

    @Autowired
    NominaServices nominaServices;
    @PostMapping("/nomina")

    public ResponseEntity<?> nominaNomina(@RequestBody NominaDto nominaDto){

        Nomina nomina = new Nomina(
                nominaDto.getTipo_contrato(),
                nominaDto.getCargo(),
                nominaDto.getJefe_a_cargo(),
                nominaDto.getC_costo_especif(),
                nominaDto.getEmail_corporativo(),
                nominaDto.getId_key(),
                nominaDto.getFecha_ingreso(),
                nominaDto.getUbicacion_laboral(),
                nominaDto.getEps(),
                nominaDto.getFondo_de_cesantias(),
                nominaDto.getFondo_de_pensiones(),
                nominaDto.getCaja_de_compensacion(),
                nominaDto.getSalario(),
                nominaDto.getAux_transporte(),
                nominaDto.getAux_comunicacion(),
                nominaDto.getAux_rodamiento(),
                nominaDto.getAux_digital(),
                nominaDto.getBanco(),
                nominaDto.getNumero_cuenta()
        );


        nominaServices.saveNominas(nomina);

        return new ResponseEntity<>(HttpStatus.CREATED);

    }

}
