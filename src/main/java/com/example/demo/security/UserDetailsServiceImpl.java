package com.example.demo.security;

import com.example.demo.Model.Entity.User;
import com.example.demo.Model.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con email: " + email));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmailPersonal())  // Spring Security usa username (en este caso, el email)
                .password(user.getPassword())  // La contraseña ya está encriptada en la BD
                .authorities(Collections.emptyList()) // No manejamos roles por ahora
                .build();
    }
}
