package com.example.demo.Controller;

import com.example.demo.Dto.DriverDto;
import com.example.demo.Model.Entity.Driver;
import com.example.demo.Services.DriverServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("x-cargo")
@RestController

public class DriverController {

    @Autowired
    DriverServices driverServices;
    @PostMapping("/driver")

    public ResponseEntity<?> driverDriver(@RequestBody DriverDto driverDto){

        Driver driver = new Driver(
                driverDto.getTipo_licencia(),
                driverDto.getLugar_expedicion(),
                driverDto.getFecha_expedicion(),
                driverDto.getFecha_vencimiento(),
                driverDto.getAños_experiencia(),
                driverDto.getAccidentes_viales(),
                driverDto.getTipo_accidentes_viales(),
                driverDto.getCantidad_accidentes(),
                driverDto.getMedio_de_transporte_propio(),
                driverDto.getCantidad_tipos_transporte(),
                driverDto.getTipos_de_transporte()
                //driverDto.getId_key

        );

        driverServices.saveDrivers(driver);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}

