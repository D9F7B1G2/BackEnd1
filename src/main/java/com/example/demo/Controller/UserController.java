package com.example.demo.Controller;

import com.example.demo.Dto.UserDto;
import com.example.demo.Model.Entity.User;
import com.example.demo.Model.Repository.UserRepository;
import com.example.demo.Services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.Base64;
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

/**
 * La función `createUser` recibe datos de un usuario, crea un objeto `User` a partir de ellos, 
 * guarda el usuario utilizando un servicio y devuelve una respuesta con un mensaje de éxito 
 * o un mensaje de error si ocurre una excepción.
 * @param userDto El método `createUser` es un endpoint POST que recibe un objeto `UserDto` en el cuerpo 
 * de la solicitud y luego crea un nuevo objeto `User` con los datos proporcionados. El objeto `User` 
 * se guarda utilizando el método `saveUsers` del servicio. Si la creación del usuario es exitosa, se...
 * @return El método `createUser` devuelve un `ResponseEntity` que contiene un `Map<String, String>`. 
 * Si la creación del usuario es exitosa, retorna una respuesta con el mensaje 
 * "Usuario creado exitosamente" y el código de estado HTTP 201 (CREATED). 
 * Si ocurre un error durante la creación, devuelve una respuesta de error con un mensaje indicando 
 * el problema y el código de estado HTTP 500 (INTERNAL SERVER ERROR).
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
                    userDto.getImgProfile());
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
 * Esta función en Java obtiene una lista de usuarios y la devuelve como un `ResponseEntity`, manejando 
 * excepciones al retornar un estado de error interno del servidor si es necesario.
 * @return Se devuelve un objeto `ResponseEntity` que contiene una lista de objetos `User`. 
 * Si la operación es exitosa, el `ResponseEntity` tendrá un estado HTTP 200 (OK) y la lista de usuarios. 
 * Si ocurre una excepción, el `ResponseEntity` tendrá un estado HTTP 500 (Internal Server Error) 
 * sin ningún cuerpo en la respuesta.
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
 * Esta función en Java actualiza el estado de los usuarios según los objetos `UserDto` proporcionados, 
 * manejando los casos en los que no se encuentra al usuario y devolviendo respuestas apropiadas.
 * @param userDtos El método `updateUsersStatus` es un mapeo PUT que actualiza el estado de los usuarios 
 * según la lista de objetos `UserDto` proporcionada. El método itera sobre cada `UserDto` en la lista, 
 * recupera la entidad `User` correspondiente del repositorio según el ID, y actualiza su estado.
 * @return El método `updateUsersStatus` devuelve un `ResponseEntity<String>`. 
 * Si la operación de actualización es exitosa, devuelve una respuesta con el código de estado 200 (OK) 
 * y el mensaje "Usuarios actualizados exitosamente". 
 * Si ocurre un error durante el proceso de actualización, devuelve una respuesta con el código de estado 
 * 500 (Internal Server Error) y un mensaje de error indicando la causa del problema.
 */
    @PutMapping("/updateStatus")
    public ResponseEntity<String> updateUsersStatus(@RequestBody List<UserDto> userDtos) {
        try {
            for (UserDto userDto : userDtos) {
                Optional<User> optionalUser = userRepository.findById(userDto.getId());
                if (optionalUser.isPresent()) {
                    User user = optionalUser.get();
                    // Actualizar solo los campos que no sean nulos (sin incluir la contraseña)
                    if (userDto.getEstadoDeRegistro() != null)
                        user.setEstadoDeRegistro(userDto.getEstadoDeRegistro());
                    // Guardar cambios sin tocar la contraseña
                    userServices.saveUsers(user);
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body("Usuario con ID " + userDto.getId() + " no encontrado");
                }
            }
            return ResponseEntity.ok("Usuarios actualizados exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar usuarios: " + e.getMessage());
        }
    }

    /*
     * Esta función de Java maneja la creación o actualización de información de
     * usuario basada en el objeto proporcionado `UserDto`.
     * - **@param userDto**: El parámetro `userDto` en el método `handleCv` es de
     * tipo `UserDto`, que probablemente sea un objeto de
     * transferencia de datos (DTO) que representa la información del usuario. Según
     * el fragmento de código proporcionado, contiene las
     * siguientes propiedades.
     * - **@return**: El método devuelve un objeto `ResponseEntity` con un mensaje
     * que indica si el usuario fue actualizado o creado exitosamente, junto con un
     * código de estado HTTP.
     * - Si el usuario ya existe en la base de datos, se retorna el mensaje
     * **"Usuario actualizado exitosamente"** con un estado **OK (200)**.
     * - Si se crea un nuevo usuario, se retorna el mensaje
     * **"Usuario creado exitosamente"** con un estado correspondiente.
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
            return new ResponseEntity<>("Error al guardar usuario: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

/**
 * Esta función en Java recupera el perfil de un usuario por su ID y devuelve el objeto del usuario 
 * o un mensaje de error si el usuario no es encontrado o si ocurre una excepción.
 * @param userId El parámetro `userId` en el método `getUserProfile` es una variable de ruta que representa 
 * el identificador único del usuario cuyo perfil se está solicitando. Este método recupera el perfil del 
 * usuario desde la base de datos usando el `userId` proporcionado. Si el usuario es encontrado, devuelve un 
 * `ResponseEntity` con...
 * @return El método devuelve un objeto `ResponseEntity`. Si el usuario con el `userId` especificado es 
 * encontrado en el repositorio, retorna un `ResponseEntity` con el objeto del usuario y estado OK. 
 * Si el usuario no es encontrado, devuelve un `ResponseEntity` con estado NOT_FOUND y el mensaje 
 * "Usuario no encontrado". Si ocurre una excepción durante el proceso, retorna un `ResponseEntity` 
 * con estado INTERNAL_SERVER_ERROR y un mensaje de error.
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
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al buscar el usuario: " + e.getMessage());
        }
    }

    /**
 * Esta función en Java se encarga de subir una imagen de perfil para un usuario identificado por su ID.
 * 
 * @param userId El parámetro `userId` en el método `uploadProfileImage` representa el identificador 
 * único del usuario para quien se está subiendo la imagen de perfil. Este parámetro se extrae de la 
 * ruta de la URL de la solicitud ("/users/{userId}/img") y se utiliza para identificar al usuario 
 * específico cuya imagen de perfil se está...
 * 
 * @param requestBody El parámetro `requestBody` en el método `uploadProfileImage` es un 
 * Map<String, String> que contiene los datos enviados en el cuerpo de la solicitud PUT. En este caso, 
 * se espera que contenga una clave "imgProfile" que contenga los datos de la imagen codificados en base64.
 * 
 * @return El método `uploadProfileImage` devuelve un objeto `ResponseEntity`. La respuesta puede ser 
 * una de las siguientes:
 */
    @PutMapping("/users/{userId}/img")
    public ResponseEntity<?> uploadProfileImage(
            @PathVariable Long userId,
            @RequestBody Map<String, String> requestBody) {
        try {
            Optional<User> userOptional = userServices.findById(userId);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                String base64Image = requestBody.get("imgProfile");
                if (base64Image == null || base64Image.isEmpty()) {
                    return ResponseEntity.badRequest().body("No se proporcionó imagen.");
                }
                byte[] imageBytes = Base64.getDecoder().decode(base64Image);
                user.setImgProfile(imageBytes);
                userServices.saveUsers(user);
                return ResponseEntity.ok("Imagen de perfil actualizada exitosamente.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al subir la imagen: " + e.getMessage());
        }
    }

    /**
 * Esta función en Java recupera la imagen de perfil de un usuario en formato base64 si existe, 
 * o devuelve mensajes de error apropiados si el usuario no se encuentra o no tiene imagen de perfil.
 * @param userId El parámetro `userId` en la anotación `@GetMapping` representa el identificador único 
 * del usuario cuya imagen de perfil se está solicitando. Este endpoint recupera la imagen de perfil 
 * del usuario con el `userId` especificado y la devuelve como una cadena codificada en base64 
 * en el cuerpo de la respuesta si el usuario existe y...
 * @return El método `getUserProfileImage` devuelve un objeto `ResponseEntity`. La respuesta puede ser 
 * una de las siguientes:
 */
    @GetMapping("/users/{userId}/img")
    public ResponseEntity<?> getUserProfileImage(@PathVariable Long userId) {
        Optional<User> userOptional = userServices.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            byte[] imageBytes = user.getImgProfile();
            if (imageBytes != null) {
                String base64Image = Base64.getEncoder().encodeToString(imageBytes);
                return ResponseEntity.ok(Collections.singletonMap("imgProfile", base64Image));
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("El usuario no tiene imagen de perfil.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
        }
    }

/**
 * Esta función en Java actualiza la información del perfil de un usuario, excluyendo la contraseña 
 * de la actualización.
 * @param userId El parámetro `userId` es una variable de ruta que representa el identificador único 
 * del usuario cuyo perfil se desea actualizar. Este método se utiliza en una aplicación Spring Boot 
 * con un mapeo PUT.
 * @param userDto El parámetro `userDto` es un objeto enviado en el cuerpo de la solicitud que contiene 
 * los nuevos datos del perfil del usuario, excluyendo la contraseña.
 * @return El método `updateUserProfile` devuelve un objeto `ResponseEntity`. Si el usuario es encontrado 
 * y el perfil se actualiza correctamente, retorna una respuesta con un mensaje JSON indicando que 
 * el perfil fue actualizado exitosamente. Si el usuario no es encontrado, retorna una respuesta con 
 * estado 404 y un mensaje de error indicando que el usuario no fue encontrado. Si ocurre una excepción 
 * durante el proceso de actualización, retorna un `ResponseEntity` con estado 500 (Internal Server Error).
 */
    @PutMapping("/labs/{userId}")
    public ResponseEntity<?> updateUserProfile(
            @PathVariable Long userId,
            @RequestBody UserDto userDto) {
        try {
            Optional<User> userOptional = userServices.findById(userId);
            if (userOptional.isPresent()) {
                User existingUser = userOptional.get();
                if (userDto.getGenero() != null)
                    existingUser.setGenero(userDto.getGenero());
                if (userDto.getRh() != null)
                    existingUser.setRh(userDto.getRh());
                if (userDto.getCelular() != null)
                    existingUser.setCelular(userDto.getCelular());
                if (userDto.getEmailPersonal() != null)
                    existingUser.setEmailPersonal(userDto.getEmailPersonal());
                if (userDto.getFechaDeNacimiento() != null)
                    existingUser.setFechaDeNacimiento(userDto.getFechaDeNacimiento());
                if (userDto.getContactoEmergencia1() != null)
                    existingUser.setContactoEmergencia1(userDto.getContactoEmergencia1());
                if (userDto.getNumeroEmergencia1() != null)
                    existingUser.setNumeroEmergencia1(userDto.getNumeroEmergencia1());
                if (userDto.getEstadoCivil() != null)
                    existingUser.setEstadoCivil(userDto.getEstadoCivil());
                if (userDto.getTieneHijos() != null)
                    existingUser.setTieneHijos(userDto.getTieneHijos());
                if (userDto.getCantidadHijos() != null)
                    existingUser.setCantidadHijos(userDto.getCantidadHijos());
                if (userDto.getTipoFamilia() != null)
                    existingUser.setTipoFamilia(userDto.getTipoFamilia());
                if (userDto.getNivelEscolaridad() != null)
                    existingUser.setNivelEscolaridad(userDto.getNivelEscolaridad());
                if (userDto.getProfesion() != null)
                    existingUser.setProfesion(userDto.getProfesion());
                if (userDto.getEstudiosEnCurso() != null)
                    existingUser.setEstudiosEnCurso(userDto.getEstudiosEnCurso());
                if (userDto.getTipoDeEstudioEnCurso() != null)
                    existingUser.setTipoDeEstudioEnCurso(userDto.getTipoDeEstudioEnCurso());
                if (userDto.getNombreEstudioEnCurso() != null)
                    existingUser.setNombreEstudioEnCurso(userDto.getNombreEstudioEnCurso());
                if (userDto.getTipoDeVivienda() != null)
                    existingUser.setTipoDeVivienda(userDto.getTipoDeVivienda());
                if (userDto.getCiudadMunicipioResidencia() != null)
                    existingUser.setCiudadMunicipioResidencia(userDto.getCiudadMunicipioResidencia());
                if (userDto.getLocalidadSoloParaBogota() != null)
                    existingUser.setLocalidadSoloParaBogota(userDto.getLocalidadSoloParaBogota());
                if (userDto.getBarrioSoloParaBogota() != null)
                    existingUser.setBarrioSoloParaBogota(userDto.getBarrioSoloParaBogota());
                if (userDto.getDireccionActual() != null)
                    existingUser.setDireccionActual(userDto.getDireccionActual());
                if (userDto.getTieneMascota() != null)
                    existingUser.setTieneMascota(userDto.getTieneMascota());
                if (userDto.getCantidadDeMascotas() != null)
                    existingUser.setCantidadDeMascotas(userDto.getCantidadDeMascotas());
                if (userDto.getNumeroDocumento() != null)
                    existingUser.setNumeroDocumento(userDto.getNumeroDocumento());
                if (userDto.getNombre1() != null)
                    existingUser.setNombre1(userDto.getNombre1());
                if (userDto.getNombre2() != null)
                    existingUser.setNombre2(userDto.getNombre2());
                if (userDto.getApellido1() != null)
                    existingUser.setApellido1(userDto.getApellido1());
                if (userDto.getApellido2() != null)
                    existingUser.setApellido2(userDto.getApellido2());
                if (userDto.getDepartamento() != null)
                    existingUser.setDepartamento(userDto.getDepartamento());
                if (userDto.getTipoDocumento() != null)
                    existingUser.setTipoDocumento(userDto.getTipoDocumento());
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

/**
 * Esta función en Java se encarga de actualizar la contraseña de un usuario, realizando validaciones 
 * y manejando posibles errores.
 * @param id El parámetro `id` en el método `updatePassword` es una variable de tipo `Long` que 
 * representa el ID del usuario cuya contraseña se desea actualizar. Se extrae como variable de ruta 
 * en el mapeo de URL `@PutMapping("/updatePassword/{id}")`.
 * @param userDto El parámetro `userDto` en el método `updatePassword` es un objeto DTO (Data Transfer Object) 
 * que contiene la información necesaria para la actualización de la contraseña del usuario. 
 * Probablemente incluye campos como:
 * - contraseña actual
 * - nueva contraseña
 * - confirmación de nueva contraseña
 * @return El método `updatePassword` devuelve un objeto `ResponseEntity`. La respuesta puede ser una 
 * de las siguientes:
 * - Si la actualización es exitosa, devuelve un estado 200 (OK) con un mensaje de éxito.
 * - Si el usuario no es encontrado o la validación falla, puede devolver un estado 400 o 404 con el 
 *   mensaje correspondiente.
 * - Si ocurre una excepción, devuelve un estado 500 (Internal Server Error) con un mensaje de error.
 */
    @PutMapping("/updatePassword/{id}")
    public ResponseEntity<?> updatePassword(
            @PathVariable Long id,
            @RequestBody UserDto userDto) {
        if (userDto.getPasswordAnterior() == null || userDto.getPassword() == null
                || userDto.getPasswordConfirmacion() == null) {
            return ResponseEntity.badRequest()
                    .body(Collections.singletonMap("error", "Todos los campos son obligatorios."));
        }
        try {
            userServices.updatePassword(id, userDto.getPasswordAnterior(), userDto.getPassword(),
                    userDto.getPasswordConfirmacion());
            return ResponseEntity.ok(Collections.singletonMap("message", "Contraseña actualizada exitosamente."));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Error interno al actualizar la contraseña."));
        }
    }

}