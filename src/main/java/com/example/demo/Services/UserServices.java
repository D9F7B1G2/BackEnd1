package com.example.demo.Services;

import com.example.demo.Dto.RegisterDto;
import com.example.demo.Model.Entity.User;
import com.example.demo.Model.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class UserServices {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Inyectamos el bean de SecurityConfig

    // Método para validar credenciales de inicio de sesión
    public User validateUser(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(password, user.getPassword())) { // Comparar con la contraseña encriptada
                return user;
            }
        }
        return null;
    }

    // Método para registrar un usuario
    public User registerUser(RegisterDto registerDto) {
        if (userRepository.findByEmail(registerDto.getEmailPersonal()).isPresent() ||
                userRepository.findByNumeroDocumento(registerDto.getNumeroDocumento()).isPresent()) {
            return null; // Usuario ya existe
        }

        User user = new User();
        user.setEmailPersonal(registerDto.getEmailPersonal());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword())); // Encriptamos la contraseña
        user.setNumeroDocumento(registerDto.getNumeroDocumento());

        return userRepository.save(user);
    }

    // Método para obtener todos los usuarios
    public List<User> getAllUsers() {
        return userRepository.findAll(); // Usa el repositorio para obtener todos los usuarios
    }

    // Método para guardar un usuario
    public void saveUsers(User user) {
        userRepository.save(user);
    }
    public User findByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.orElse(null); // Devuelve el usuario si lo encuentra, o null si no existe
    }
}
