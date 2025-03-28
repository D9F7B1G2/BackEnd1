package com.example.demo.Controller;

import java.sql.Connection;  
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private DataSource dataSource;

    @GetMapping("/db")
    public ResponseEntity<String> testDbConnection() {
        try (Connection conn = dataSource.getConnection()) {  // <-- Uso correcto de java.sql.Connection
            return ResponseEntity.ok("Conexión exitosa a la base de datos.");
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error conectando a la base de datos: " + e.getMessage());
        }
    }
}
