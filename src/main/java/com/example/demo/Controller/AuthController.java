package com.example.demo.Controller;

import com.example.demo.Dto.LoginDto;
import com.example.demo.Dto.RegisterDto;
import com.example.demo.Model.Entity.User;
import com.example.demo.Services.UserServices;
import com.example.demo.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Configuration
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    @Autowired
    private UserServices userServices;

    @Autowired
    private PasswordEncoder passwordEncoder; // Inyectamos correctamente el PasswordEncoder

    @Autowired
    private JwtUtil jwtUtil;

    // Endpoint para login
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginDto loginDto) {
        try {
            String email = loginDto.getEmailPersonal();
            String password = loginDto.getPassword();

            User user = userServices.findByEmail(email);
            if (user == null) {
                return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
            }

            if (user.getPassword() == null) {
                return new ResponseEntity<>("Contraseña no establecida", HttpStatus.BAD_REQUEST);
            }

            // Verificar contraseña
            if (passwordEncoder.matches(password, user.getPassword())) {
                // Generar el token JWT
                String token = jwtUtil.generateToken(email);

                // Enviar respuesta con el token
                return ResponseEntity.ok().body("{\"token\": \"" + token + "\"}");
            } else {
                return new ResponseEntity<>("Contraseña incorrecta", HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error interno: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDto registerDto) {
        System.out.println("Datos recibidos para registro: " + registerDto);

        if (registerDto.getPassword() == null || registerDto.getEmailPersonal() == null || registerDto.getNumeroDocumento() == null) {
            return ResponseEntity.badRequest().body("Faltan campos obligatorios. Por favor, complete todos los datos.");
        }

        try {
            // Encriptar la contraseña antes de guardarla
            registerDto.setPassword(passwordEncoder.encode(registerDto.getPassword()));

            User newUser = userServices.registerUser(registerDto);
            if (newUser != null) {
                System.out.println("Usuario registrado exitosamente: " + newUser);
                return ResponseEntity.ok("Usuario registrado exitosamente");
            } else {
                System.out.println("Error: Usuario ya existe.");
                return ResponseEntity.status(500).body("Usuario ya registrado. Intente con otro correo o número de documento.");
            }
        } catch (Exception e) {
            System.err.println("Excepción durante el registro: " + e.getMessage());
            return ResponseEntity.status(500).body("Error interno del servidor. Intente más tarde.");
        }
    }
}
