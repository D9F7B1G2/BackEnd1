package com.example.demo.security;

import com.example.demo.Model.Entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class UserDetailsImpl implements UserDetails {

    private final String emailPersonal;
    private final String password;
    private final boolean enabled;

    public UserDetailsImpl(String username, String password, boolean enabled) {
        this.emailPersonal = username;
        this.password = password;
        this.enabled = enabled;
    }

    public static UserDetailsImpl build(User user) {
        return new UserDetailsImpl(user.getEmailPersonal(), user.getPassword(), true); // Asumimos que siempre está habilitado
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList(); // Se pueden agregar roles aquí si es necesario
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return emailPersonal;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
