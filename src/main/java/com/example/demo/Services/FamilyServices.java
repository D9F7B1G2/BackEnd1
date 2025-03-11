package com.example.demo.Services;

import com.example.demo.Model.Entity.Family;
import com.example.demo.Model.Repository.FamilyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class FamilyServices {

    @Autowired
    FamilyRepository familyRepository;

    public void savefamilys(Family family){
        familyRepository.save(family);
    }

}
