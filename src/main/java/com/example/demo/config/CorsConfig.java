//package com.example.demo.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//import org.springframework.web.filter.CorsFilter;
//
//@Configuration
//public class CorsConfig {
//
//    @Bean
//    public CorsFilter corsFilter() {
//        CorsConfiguration config = new CorsConfiguration();
//        config.addAllowedOrigin("http://localhost:4200"); // Origen permitido (Angular en localhost)
//        config.addAllowedHeader("*"); // Permitir todos los encabezados
//        config.addAllowedMethod("*"); // Permitir todos los métodos HTTP (GET, POST, PUT, DELETE, etc.)
//        config.setAllowCredentials(true); // Permitir envío de cookies o credenciales
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", config); // Aplicar configuración a todos los endpoints
//        return new CorsFilter(source);
//    }
//}
