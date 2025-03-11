package com.example.demo.Model.Repository;

import com.example.demo.Model.Entity.Family;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FamilyRepository extends JpaRepository<Family, Long>{
}
