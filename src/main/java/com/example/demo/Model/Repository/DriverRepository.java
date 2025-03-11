package com.example.demo.Model.Repository;

import com.example.demo.Model.Entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverRepository extends JpaRepository<Driver, Long>{
}
