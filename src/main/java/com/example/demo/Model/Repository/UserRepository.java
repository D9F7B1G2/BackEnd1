package com.example.demo.Model.Repository;

import com.example.demo.Model.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Consulta personalizada para encontrar un usuario por email
    @Query("SELECT u FROM User u WHERE u.emailPersonal = :email")
    Optional<User> findByEmail(@Param("email") String email);

    // Consulta personalizada para encontrar un usuario por número de documento
    @Query("SELECT u FROM User u WHERE u.numeroDocumento = :numeroDocumento")
    Optional<User> findByNumeroDocumento(@Param("numeroDocumento") String numeroDocumento);
}
