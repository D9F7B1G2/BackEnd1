package com.example.demo.Dto;

import lombok.Data;

@Data
public class RegisterDto {
    private String emailPersonal;     // Correo electrónico personal del usuario
    private String numeroDocumento;   // Número de documento del usuario
    private String password;          // Contraseña
}
