// src/main/java/com/example/demo/Dto/LoginResponse.java
package com.example.demo.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private Long Id;
    private String idRol;
}
