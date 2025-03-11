package com.example.demo.Services;

import com.example.demo.Model.Entity.Sst;
import com.example.demo.Model.Repository.SstRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class SstServices {

    @Autowired
    SstRepository sstRepository;

    public void savessts(Sst sst) {
        sstRepository.save(sst);
    }

}
