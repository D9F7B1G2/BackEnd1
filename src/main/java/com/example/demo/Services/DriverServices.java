package com.example.demo.Services;

import com.example.demo.Model.Entity.Driver;
import com.example.demo.Model.Repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class DriverServices {

    @Autowired
    DriverRepository driverRepository;

    public void saveDrivers(Driver driver){
        driverRepository.save(driver);
    }
}
