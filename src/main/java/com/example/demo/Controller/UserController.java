// UserController.java
package com.example.demo.Controller;

import com.example.demo.Dto.UserDto;
import com.example.demo.Model.Entity.User;
import com.example.demo.Model.Repository.UserRepository;
import com.example.demo.Services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Configuration
@RestController
@RequestMapping("x-cargo")
@CrossOrigin(origins = "https://portalxcargo-600185687970.us-central1.run.app")
public class UserController {

    @Autowired
    private UserServices userServices;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * Crear un nuevo usuario.
     */
    @PostMapping("/create")
    public ResponseEntity<Map<String, String>> createUser(@RequestBody UserDto userDto) {
        try {
            // Crear un objeto User desde UserDto
            User user = new User(
                    userDto.getId(),
                    userDto.getGenero(),
                    userDto.getRh(),
                    userDto.getCelular(),
                    userDto.getEmailPersonal(),
                    userDto.getFechaDeNacimiento(),
                    userDto.getContactoEmergencia1(),
                    userDto.getNumeroEmergencia1(),
                    userDto.getEstadoCivil(),
                    userDto.getTieneHijos(),
                    userDto.getCantidadHijos(),
                    userDto.getTipoFamilia(),
                    userDto.getNivelEscolaridad(),
                    userDto.getProfesion(),
                    userDto.getEstudiosEnCurso(),
                    userDto.getTipoDeEstudioEnCurso(),
                    userDto.getNombreEstudioEnCurso(),
                    userDto.getTipoDeVivienda(),
                    userDto.getCiudadMunicipioResidencia(),
                    userDto.getLocalidadSoloParaBogota(),
                    userDto.getBarrioSoloParaBogota(),
                    userDto.getDireccionActual(),
                    userDto.getTieneMascota(),
                    userDto.getCantidadDeMascotas(),
                    userDto.getNumeroDocumento(),
                    userDto.getNombre1(),
                    userDto.getNombre2(),
                    userDto.getApellido1(),
                    userDto.getApellido2(),
                    userDto.getPassword(),
                    userDto.getDepartamento(),
                    userDto.getTipoDocumento(),
                    userDto.getEstadoDeRegistro(),
                    userDto.getCvProfile(),
                    userDto.getImgProfile()
            );

            // Guardar el usuario usando el servicio
            userServices.saveUsers(user);

            Map<String, String> response = new HashMap<>();
            response.put("message", "Usuario creado exitosamente");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Error al crear el usuario: " + e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Obtener la lista de usuarios.
     */
    @GetMapping("/list")
    public ResponseEntity<List<User>> listUsers() {
        try {
            List<User> users = userServices.getAllUsers();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Crear o actualizar un usuario desde lista de Usuarios o Perfil de usuario.
     */
    @PutMapping("/updateStatus")
    public ResponseEntity<String> updateUsersStatus(@RequestBody List<UserDto> userDtos) {
        try {
            for (UserDto userDto : userDtos) {
                Optional<User> optionalUser = userRepository.findById(userDto.getId());
                if (optionalUser.isPresent()) {
                    User user = optionalUser.get();

                    // Actualizar solo los campos que no sean nulos
                    if (userDto.getEstadoDeRegistro() != null) user.setEstadoDeRegistro(userDto.getEstadoDeRegistro());
                    if (userDto.getRh() != null) user.setRh(userDto.getRh());
                    if (userDto.getGenero() != null) user.setGenero(userDto.getGenero());
                    if (userDto.getCelular() != null) user.setCelular(userDto.getCelular());
                    if (userDto.getEmailPersonal() != null) user.setEmailPersonal(userDto.getEmailPersonal());
                    if (userDto.getFechaDeNacimiento() != null) user.setFechaDeNacimiento(userDto.getFechaDeNacimiento());
                    if (userDto.getContactoEmergencia1() != null) user.setContactoEmergencia1(userDto.getContactoEmergencia1());
                    if (userDto.getNumeroEmergencia1() != null) user.setNumeroEmergencia1(userDto.getNumeroEmergencia1());
                    if (userDto.getEstadoCivil() != null) user.setEstadoCivil(userDto.getEstadoCivil());
                    if (userDto.getTieneHijos() != null) user.setTieneHijos(userDto.getTieneHijos());
                    if (userDto.getCantidadHijos() != null) user.setCantidadHijos(userDto.getCantidadHijos());
                    if (userDto.getTipoFamilia() != null) user.setTipoFamilia(userDto.getTipoFamilia());
                    if (userDto.getNivelEscolaridad() != null) user.setNivelEscolaridad(userDto.getNivelEscolaridad());
                    if (userDto.getProfesion() != null) user.setProfesion(userDto.getProfesion());
                    if (userDto.getEstudiosEnCurso() != null) user.setEstudiosEnCurso(userDto.getEstudiosEnCurso());
                    if (userDto.getTipoDeEstudioEnCurso() != null) user.setTipoDeEstudioEnCurso(userDto.getTipoDeEstudioEnCurso());
                    if (userDto.getNombreEstudioEnCurso() != null) user.setNombreEstudioEnCurso(userDto.getNombreEstudioEnCurso());
                    if (userDto.getTipoDeVivienda() != null) user.setTipoDeVivienda(userDto.getTipoDeVivienda());
                    if (userDto.getCiudadMunicipioResidencia() != null) user.setCiudadMunicipioResidencia(userDto.getCiudadMunicipioResidencia());
                    if (userDto.getLocalidadSoloParaBogota() != null) user.setLocalidadSoloParaBogota(userDto.getLocalidadSoloParaBogota());
                    if (userDto.getBarrioSoloParaBogota() != null) user.setBarrioSoloParaBogota(userDto.getBarrioSoloParaBogota());
                    if (userDto.getDireccionActual() != null) user.setDireccionActual(userDto.getDireccionActual());
                    if (userDto.getTieneMascota() != null) user.setTieneMascota(userDto.getTieneMascota());
                    if (userDto.getCantidadDeMascotas() != null) user.setCantidadDeMascotas(userDto.getCantidadDeMascotas());
                    if (userDto.getNumeroDocumento() != null) user.setNumeroDocumento(userDto.getNumeroDocumento());
                    if (userDto.getNombre1() != null) user.setNombre1(userDto.getNombre1());
                    if (userDto.getNombre2() != null) user.setNombre2(userDto.getNombre2());
                    if (userDto.getApellido1() != null) user.setApellido1(userDto.getApellido1());
                    if (userDto.getApellido2() != null) user.setApellido2(userDto.getApellido2());
                    if (userDto.getDepartamento() != null) user.setDepartamento(userDto.getDepartamento());
                    if (userDto.getTipoDocumento() != null) user.setTipoDocumento(userDto.getTipoDocumento());
                    if (userDto.getImgProfile() != null) user.setImgProfile(userDto.getImgProfile());
                    if (userDto.getCvProfile() != null) user.setCvProfile(userDto.getCvProfile());

                    if (userDto.getPassword() != null) user.setPassword(passwordEncoder.encode(userDto.getPassword()));

                    // Guardar los cambios
                    userServices.saveUsers(user);
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario con ID " + userDto.getId() + " no encontrado");
                }
            }
            return ResponseEntity.ok("Estados de usuarios actualizados exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar usuarios: " + e.getMessage());
        }
    }


    @PostMapping("/cv")
    public ResponseEntity<?> handleCv(@RequestBody UserDto userDto) {
        try {
            Optional<User> existingUser = userRepository.findByNumeroDocumento(userDto.getNumeroDocumento());
            if (existingUser.isPresent()) {
                User user = existingUser.get();
                user.setGenero(userDto.getGenero());
                user.setRh(userDto.getRh());
                user.setCelular(userDto.getCelular());
                user.setEmailPersonal(userDto.getEmailPersonal());
                // Actualizar más campos según sea necesario
                userServices.saveUsers(user);
                return new ResponseEntity<>("Usuario actualizado exitosamente", HttpStatus.OK);
            } else {
                User newUser = new User();
                newUser.setNumeroDocumento(userDto.getNumeroDocumento());
                newUser.setEmailPersonal(userDto.getEmailPersonal());
                // Agregar más campos según sea necesario
                userServices.saveUsers(newUser);
                return new ResponseEntity<>("Usuario creado exitosamente", HttpStatus.CREATED);
            }
        } catch (Exception e) {
            // Capturar y devolver el error exacto
            return new ResponseEntity<>("Error al guardar usuario: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Obtener perfil de usuario por ID.
     */
    @GetMapping("/labs/{userId}")
    public ResponseEntity<?> getUserProfile(@PathVariable Long userId) {
        try {
            Optional<User> user = userRepository.findById(userId);
            if (user.isPresent()) {
                return ResponseEntity.ok(user.get());
            } else {
                // Devuelve un ResponseEntity con un mensaje genérico
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
            }
        } catch (Exception e) {
            // Devuelve un mensaje de error genérico
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar el usuario: " + e.getMessage());
        }
    }


    /**
     * Actualizar perfil de usuario por ID.
     */
    @PutMapping("/labs/{userId}")
    public ResponseEntity<String> updateUserProfile(
            @PathVariable Long userId,
            @RequestBody UserDto userDto) {
        try {
            Optional<User> userOptional = userRepository.findById(userId);

            if (userOptional.isPresent()) {
                User existingUser = userOptional.get();
                if (userDto.getGenero() != null) existingUser.setGenero(userDto.getGenero());
                userServices.saveUsers(existingUser);
                return ResponseEntity.ok("Perfil actualizado exitosamente");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el perfil: " + e.getMessage());
        }
    }
}
