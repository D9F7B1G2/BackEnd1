package com.example.demo.Services;

import com.example.demo.Model.Entity.Nomina;
import com.example.demo.Model.Repository.NominaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class NominaServices {

    @Autowired
    NominaRepository nominaRepository;

    public void saveNominas(Nomina nomina) {
        nominaRepository.save(nomina);
    }

}
