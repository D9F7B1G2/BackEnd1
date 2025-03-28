package com.example.demo.Controller;

import com.example.demo.Dto.UserDto;
import com.example.demo.Model.Entity.User;
import com.example.demo.Model.Repository.UserRepository;
import com.example.demo.Services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("x-cargo")
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
     * Actualizar un usuario desde lista de Usuarios o Perfil de usuario. cv.component y Labs.component
     */
    @PutMapping("/updateStatus")
    public ResponseEntity<String> updateUsersStatus(@RequestBody List<UserDto> userDtos) {
        try {
            for (UserDto userDto : userDtos) {
                Optional<User> optionalUser = userRepository.findById(userDto.getId());
                if (optionalUser.isPresent()) {
                    User user = optionalUser.get();

                    // Actualizar solo los campos que no sean nulos (sin incluir la contraseña)
                    if (userDto.getEstadoDeRegistro() != null) user.setEstadoDeRegistro(userDto.getEstadoDeRegistro());
                    // Guardar cambios sin tocar la contraseña
                    userServices.saveUsers(user);
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario con ID " + userDto.getId() + " no encontrado");
                }
            }
            return ResponseEntity.ok("Usuarios actualizados exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar usuarios: " + e.getMessage());
        }
    }


    /*
     * Endpoint para actualizar la contraseña del usuario
     */
    @PutMapping("/updatePassword")
    public ResponseEntity<String> updateUserPassword(@RequestBody UserDto userDto) {
        Optional<User> optionalUser = userRepository.findById(userDto.getId());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (passwordEncoder.matches(userDto.getPasswordAnterior(), user.getPassword())) {
                if (userDto.getPassword().equals(userDto.getPasswordConfirmacion())) {
                    user.setPassword(passwordEncoder.encode(userDto.getPassword()));
                    userServices.saveUsers(user);
                    return ResponseEntity.ok("Contraseña actualizada correctamente");
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La nueva contraseña y la confirmación no coinciden.");
                }
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("La contraseña anterior es incorrecta.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario con ID " + userDto.getId() + " no encontrado");
        }
    }



/*Esta función de Java maneja la creación o actualización de información de usuario basada en el objeto proporcionado `UserDto`.  

- **@param userDto**: El parámetro `userDto` en el método `handleCv` es de tipo `UserDto`, que probablemente sea un objeto de 
transferencia de datos (DTO) que representa la información del usuario. Según el fragmento de código proporcionado, contiene las
siguientes propiedades.  
- **@return**: El método devuelve un objeto `ResponseEntity` con un mensaje que indica si el usuario fue actualizado o creado exitosamente, junto con un código de estado HTTP.  
  - Si el usuario ya existe en la base de datos, se retorna el mensaje **"Usuario actualizado exitosamente"** con un estado **OK (200)**.  
  - Si se crea un nuevo usuario, se retorna el mensaje **"Usuario creado exitosamente"** con un estado correspondiente.
*/
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
public ResponseEntity<?> updateUserProfile(
        @PathVariable Long userId,
        @RequestBody UserDto userDto) {
    try {
        Optional<User> userOptional = userServices.findById(userId);
        if (userOptional.isPresent()) {
            User existingUser = userOptional.get();

            // Excluir la contraseña de la actualización
            if (userDto.getGenero() != null) existingUser.setGenero(userDto.getGenero());
            if (userDto.getRh() != null) existingUser.setRh(userDto.getRh());
            if (userDto.getCelular() != null) existingUser.setCelular(userDto.getCelular());
            if (userDto.getEmailPersonal() != null) existingUser.setEmailPersonal(userDto.getEmailPersonal());
            if (userDto.getFechaDeNacimiento() != null) existingUser.setFechaDeNacimiento(userDto.getFechaDeNacimiento());
            if (userDto.getContactoEmergencia1() != null) existingUser.setContactoEmergencia1(userDto.getContactoEmergencia1());
            if (userDto.getNumeroEmergencia1() != null) existingUser.setNumeroEmergencia1(userDto.getNumeroEmergencia1());
            if (userDto.getEstadoCivil() != null) existingUser.setEstadoCivil(userDto.getEstadoCivil());
            if (userDto.getTieneHijos() != null) existingUser.setTieneHijos(userDto.getTieneHijos());
            if (userDto.getCantidadHijos() != null) existingUser.setCantidadHijos(userDto.getCantidadHijos());
            if (userDto.getTipoFamilia() != null) existingUser.setTipoFamilia(userDto.getTipoFamilia());
            if (userDto.getNivelEscolaridad() != null) existingUser.setNivelEscolaridad(userDto.getNivelEscolaridad());
            if (userDto.getProfesion() != null) existingUser.setProfesion(userDto.getProfesion());
            if (userDto.getEstudiosEnCurso() != null) existingUser.setEstudiosEnCurso(userDto.getEstudiosEnCurso());
            if (userDto.getTipoDeEstudioEnCurso() != null) existingUser.setTipoDeEstudioEnCurso(userDto.getTipoDeEstudioEnCurso());
            if (userDto.getNombreEstudioEnCurso() != null) existingUser.setNombreEstudioEnCurso(userDto.getNombreEstudioEnCurso());
            if (userDto.getTipoDeVivienda() != null) existingUser.setTipoDeVivienda(userDto.getTipoDeVivienda());
            if (userDto.getCiudadMunicipioResidencia() != null) existingUser.setCiudadMunicipioResidencia(userDto.getCiudadMunicipioResidencia());
            if (userDto.getLocalidadSoloParaBogota() != null) existingUser.setLocalidadSoloParaBogota(userDto.getLocalidadSoloParaBogota());
            if (userDto.getBarrioSoloParaBogota() != null) existingUser.setBarrioSoloParaBogota(userDto.getBarrioSoloParaBogota());
            if (userDto.getDireccionActual() != null) existingUser.setDireccionActual(userDto.getDireccionActual());
            if (userDto.getTieneMascota() != null) existingUser.setTieneMascota(userDto.getTieneMascota());
            if (userDto.getCantidadDeMascotas() != null) existingUser.setCantidadDeMascotas(userDto.getCantidadDeMascotas());
            if (userDto.getNumeroDocumento() != null) existingUser.setNumeroDocumento(userDto.getNumeroDocumento());
            if (userDto.getNombre1() != null) existingUser.setNombre1(userDto.getNombre1());
            if (userDto.getNombre2() != null) existingUser.setNombre2(userDto.getNombre2());
            if (userDto.getApellido1() != null) existingUser.setApellido1(userDto.getApellido1());
            if (userDto.getApellido2() != null) existingUser.setApellido2(userDto.getApellido2());
            if (userDto.getDepartamento() != null) existingUser.setDepartamento(userDto.getDepartamento());
            if (userDto.getTipoDocumento() != null) existingUser.setTipoDocumento(userDto.getTipoDocumento());
            if (userDto.getImgProfile() != null) existingUser.setImgProfile(userDto.getImgProfile());
            if (userDto.getCvProfile() != null) existingUser.setCvProfile(userDto.getCvProfile());

            // Guardar cambios sin tocar la contraseña
            userServices.saveUsers(existingUser);

            // Respuesta JSON válida
            return ResponseEntity.ok(Collections.singletonMap("message", "Perfil actualizado exitosamente"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("error", "Usuario no encontrado"));
        }
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Collections.singletonMap("error", "Error al actualizar el perfil: " + e.getMessage()));
    }
}

}