package com.example.demo.Controller;

import com.example.demo.Dto.FamilyDto;
import com.example.demo.Model.Entity.Family;
import com.example.demo.Services.FamilyServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("x-cargo")
@RestController
public class FamilyController {

    @Autowired
    FamilyServices familyServices;
    @PostMapping("/family")

    public ResponseEntity<?> familyFamily(@RequestBody FamilyDto familyDto){

        Family family = new Family(
                familyDto.getNombre_completo_hijo_1(),
                familyDto.getTipo_documento_hijo_1(),
                familyDto.getNumero_documento_hijo_1(),
                familyDto.getFecha_nacimiento_hijo_1(),
                familyDto.getSexo_hijo_1(),
                familyDto.getTipo_documento_hijo_2(),
                familyDto.getNumero_documento_hijo_2(),
                familyDto.getFecha_nacimiento_hijo_2(),
                familyDto.getSexo_hijo_2(),
                familyDto.getNombre_completo_hijo_2()
                //familyDto.getId_key
        );

        familyServices.savefamilys(family);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
