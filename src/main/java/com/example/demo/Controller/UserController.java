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
     * La función `createUser` recibe datos de un usuario, crea un objeto `User` a
     * partir de ellos,
     * guarda el usuario utilizando un servicio y devuelve una respuesta con un
     * mensaje de éxito
     * o un mensaje de error si ocurre una excepción.
     * 
     * @param userDto El método `createUser` es un endpoint POST que recibe un
     *                objeto `UserDto` en el cuerpo
     *                de la solicitud y luego crea un nuevo objeto `User` con los
     *                datos proporcionados. El objeto `User`
     *                se guarda utilizando el método `saveUsers` del servicio. Si la
     *                creación del usuario es exitosa, se...
     * @return El método `createUser` devuelve un `ResponseEntity` que contiene un
     *         `Map<String, String>`.
     *         Si la creación del usuario es exitosa, retorna una respuesta con el
     *         mensaje
     *         "Usuario creado exitosamente" y el código de estado HTTP 201
     *         (CREATED).
     *         Si ocurre un error durante la creación, devuelve una respuesta de
     *         error con un mensaje indicando
     *         el problema y el código de estado HTTP 500 (INTERNAL SERVER ERROR).
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
                    userDto.getImgProfile(),
                    userDto.getUbicacionLab(),
                    userDto.getCedulaPdf(),
                    userDto.getCv(),
                    userDto.getCertifLaboral(),
                    userDto.getCertifAcademica(),
                    userDto.getCertifEps(),
                    userDto.getCertifFondoPension(),
                    userDto.getCertifArl(),
                    userDto.getCertifBancario(),
                    userDto.getCertifAntecedDisciplinario(),
                    userDto.getCertifAntecedFiscales(),
                    userDto.getCertifAntecedPenales(),
                    userDto.getLibretaMilitar(),
                    userDto.getCertifMedOcupacional(),
                    userDto.getReferPersonales(),

                    userDto.getIdRol()

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
     * Esta función en Java obtiene una lista de usuarios y la devuelve como un
     * `ResponseEntity`, manejando
     * excepciones al retornar un estado de error interno del servidor si es
     * necesario.
     * 
     * @return Se devuelve un objeto `ResponseEntity` que contiene una lista de
     *         objetos `User`.
     *         Si la operación es exitosa, el `ResponseEntity` tendrá un estado HTTP
     *         200 (OK) y la lista de usuarios.
     *         Si ocurre una excepción, el `ResponseEntity` tendrá un estado HTTP
     *         500 (Internal Server Error)
     *         sin ningún cuerpo en la respuesta.
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
     * Esta función en Java actualiza el estado de los usuarios según los objetos
     * `UserDto` proporcionados,
     * manejando los casos en los que no se encuentra al usuario y devolviendo
     * respuestas apropiadas.
     * 
     * @param userDtos El método `updateUsersStatus` es un mapeo PUT que actualiza
     *                 el estado de los usuarios
     *                 según la lista de objetos `UserDto` proporcionada. El método
     *                 itera sobre cada `UserDto` en la lista,
     *                 recupera la entidad `User` correspondiente del repositorio
     *                 según el ID, y actualiza su estado.
     * @return El método `updateUsersStatus` devuelve un `ResponseEntity<String>`.
     *         Si la operación de actualización es exitosa, devuelve una respuesta
     *         con el código de estado 200 (OK)
     *         y el mensaje "Usuarios actualizados exitosamente".
     *         Si ocurre un error durante el proceso de actualización, devuelve una
     *         respuesta con el código de estado
     *         500 (Internal Server Error) y un mensaje de error indicando la causa
     *         del problema.
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
     * Esta función en Java recupera el perfil de un usuario por su ID y devuelve el
     * objeto del usuario
     * o un mensaje de error si el usuario no es encontrado o si ocurre una
     * excepción.
     * 
     * @param userId El parámetro `userId` en el método `getUserProfile` es una
     *               variable de ruta que representa
     *               el identificador único del usuario cuyo perfil se está
     *               solicitando. Este método recupera el perfil del
     *               usuario desde la base de datos usando el `userId`
     *               proporcionado. Si el usuario es encontrado, devuelve un
     *               `ResponseEntity` con...
     * @return El método devuelve un objeto `ResponseEntity`. Si el usuario con el
     *         `userId` especificado es
     *         encontrado en el repositorio, retorna un `ResponseEntity` con el
     *         objeto del usuario y estado OK.
     *         Si el usuario no es encontrado, devuelve un `ResponseEntity` con
     *         estado NOT_FOUND y el mensaje
     *         "Usuario no encontrado". Si ocurre una excepción durante el proceso,
     *         retorna un `ResponseEntity`
     *         con estado INTERNAL_SERVER_ERROR y un mensaje de error.
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
     * Esta función en Java se encarga de subir una imagen de perfil para un usuario
     * identificado por su ID.
     * 
     * @param userId      El parámetro `userId` en el método `uploadProfileImage`
     *                    representa el identificador
     *                    único del usuario para quien se está subiendo la imagen de
     *                    perfil. Este parámetro se extrae de la
     *                    ruta de la URL de la solicitud ("/users/{userId}/img") y
     *                    se utiliza para identificar al usuario
     *                    específico cuya imagen de perfil se está...
     * 
     * @param requestBody El parámetro `requestBody` en el método
     *                    `uploadProfileImage` es un
     *                    Map<String, String> que contiene los datos enviados en el
     *                    cuerpo de la solicitud PUT. En este caso,
     *                    se espera que contenga una clave "imgProfile" que contenga
     *                    los datos de la imagen codificados en base64.
     * 
     * @return El método `uploadProfileImage` devuelve un objeto `ResponseEntity`.
     *         La respuesta puede ser
     *         una de las siguientes:
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
     * Esta función en Java recupera la imagen de perfil de un usuario en formato
     * base64 si existe,
     * o devuelve mensajes de error apropiados si el usuario no se encuentra o no
     * tiene imagen de perfil.
     * 
     * @param userId El parámetro `userId` en la anotación `@GetMapping` representa
     *               el identificador único
     *               del usuario cuya imagen de perfil se está solicitando. Este
     *               endpoint recupera la imagen de perfil
     *               del usuario con el `userId` especificado y la devuelve como una
     *               cadena codificada en base64
     *               en el cuerpo de la respuesta si el usuario existe y...
     * @return El método `getUserProfileImage` devuelve un objeto `ResponseEntity`.
     *         La respuesta puede ser
     *         una de las siguientes:
     */
    @GetMapping("/profile-image/{id}")
    public ResponseEntity<String> getProfileImage(@PathVariable Long id) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isPresent()) {
            byte[] imageBytes = userOpt.get().getImgProfile();
            if (imageBytes != null && imageBytes.length > 0) {
                String base64Image = Base64.getEncoder().encodeToString(imageBytes);
                return ResponseEntity.ok().body(base64Image);
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Esta función en Java actualiza la información del perfil de un usuario,
     * excluyendo la contraseña
     * de la actualización.
     * 
     * @param userId  El parámetro `userId` es una variable de ruta que representa
     *                el identificador único
     *                del usuario cuyo perfil se desea actualizar. Este método se
     *                utiliza en una aplicación Spring Boot
     *                con un mapeo PUT.
     * @param userDto El parámetro `userDto` es un objeto enviado en el cuerpo de la
     *                solicitud que contiene
     *                los nuevos datos del perfil del usuario, excluyendo la
     *                contraseña.
     * @return El método `updateUserProfile` devuelve un objeto `ResponseEntity`. Si
     *         el usuario es encontrado
     *         y el perfil se actualiza correctamente, retorna una respuesta con un
     *         mensaje JSON indicando que
     *         el perfil fue actualizado exitosamente. Si el usuario no es
     *         encontrado, retorna una respuesta con
     *         estado 404 y un mensaje de error indicando que el usuario no fue
     *         encontrado. Si ocurre una excepción
     *         durante el proceso de actualización, retorna un `ResponseEntity` con
     *         estado 500 (Internal Server Error).
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
     * Esta función en Java se encarga de actualizar la contraseña de un usuario,
     * realizando validaciones
     * y manejando posibles errores.
     * 
     * @param id      El parámetro `id` en el método `updatePassword` es una
     *                variable de tipo `Long` que
     *                representa el ID del usuario cuya contraseña se desea
     *                actualizar. Se extrae como variable de ruta
     *                en el mapeo de URL `@PutMapping("/updatePassword/{id}")`.
     * @param userDto El parámetro `userDto` en el método `updatePassword` es un
     *                objeto DTO (Data Transfer Object)
     *                que contiene la información necesaria para la actualización de
     *                la contraseña del usuario.
     *                Probablemente incluye campos como:
     *                - contraseña actual
     *                - nueva contraseña
     *                - confirmación de nueva contraseña
     * @return El método `updatePassword` devuelve un objeto `ResponseEntity`. La
     *         respuesta puede ser una
     *         de las siguientes:
     *         - Si la actualización es exitosa, devuelve un estado 200 (OK) con un
     *         mensaje de éxito.
     *         - Si el usuario no es encontrado o la validación falla, puede
     *         devolver un estado 400 o 404 con el
     *         mensaje correspondiente.
     *         - Si ocurre una excepción, devuelve un estado 500 (Internal Server
     *         Error) con un mensaje de error.
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

    /**
     * This Java function updates a user's cedula (identification document) by
     * decoding a base64 string and
     * saving it to the user entity.
     * 
     * @param userId      The `userId` parameter in the `updateCedula` method
     *                    represents the unique identifier
     *                    of the user whose cedula (identification document) is
     *                    being updated. This parameter is extracted
     *                    from the path of the request URL
     *                    ("/users/{userId}/cedula") and is used to locate the
     *                    specific user
     *                    in the
     * @param requestBody The `updateCedula` method you provided is a PUT mapping
     *                    that updates the cedula
     *                    (identity document) of a user identified by `userId`. The
     *                    method expects a JSON request body with a
     *                    key "cedulaPdf" containing the base64 encoded cedula
     *                    document.
     * @return The `updateCedula` method returns a `ResponseEntity` object. The
     *         response can be one of the
     *         following based on the conditions:
     */
    @PutMapping("/users/{userId}/cedula")
    public ResponseEntity<?> updateCedula(@PathVariable Long userId, @RequestBody Map<String, String> requestBody) {
        try {
            Optional<User> userOptional = userServices.findById(userId);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                String base64Cedula = requestBody.get("cedulaPdf");
                if (base64Cedula == null || base64Cedula.isEmpty()) {
                    return ResponseEntity.badRequest().body("No se proporcionó cédula.");
                }
                byte[] cedulaPdf = Base64.getDecoder().decode(base64Cedula);
                user.setCedulaPdf(cedulaPdf); // Reemplaza el archivo anterior
                userServices.saveUsers(user);
                return ResponseEntity.ok("Cédula actualizada exitosamente.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al subir la cédula: " + e.getMessage());
        }
    }

        /**
     * This Java function retrieves a user's cedula PDF file as a Base64 encoded
     * string.
     * 
     * @param userId The code snippet you provided is a Spring MVC controller method
     *               that retrieves a
     *               user's cedula (identification document) in PDF format and
     *               returns it as a Base64 encoded string in
     *               the response entity.
     * @return The method is returning a ResponseEntity with either a base64 encoded
     *         Cedula PDF file if
     *         found, a message indicating that the Cedula was not found, a message
     *         indicating that the User was
     *         not found, or an error message if an exception occurs while trying to
     *         retrieve the Cedula.
     */
    @GetMapping("/users/{userId}/cedulaPdf")
    public ResponseEntity<?> getCedulaPdf(@PathVariable Long userId) {
        try {
            Optional<User> userOptional = userServices.findById(userId);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                byte[] cedulaPdf = user.getCedulaPdf();
                if (cedulaPdf == null || cedulaPdf.length == 0) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cédula no encontrada.");
                }
                String base64CedulaPdf = Base64.getEncoder().encodeToString(cedulaPdf);
                return ResponseEntity.ok(base64CedulaPdf);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener la cédula: " + e.getMessage());
        }
    }

    /**
     * This Java function updates a user's CV by decoding a Base64 string provided
     * in the request body and
     * saving it to the user entity.
     * 
     * @param userId      The `userId` parameter in the `@PutMapping` annotation
     *                    represents the unique
     *                    identifier of the user whose CV is being updated. This
     *                    identifier is used to locate the specific
     *                    user in the system before updating their CV.
     * @param requestBody The `updateCv` method is a PUT mapping that allows
     *                    updating the CV (curriculum
     *                    vitae) of a user identified by their `userId`. The method
     *                    expects a JSON request body with a key
     *                    "cv" containing the CV data encoded in base64 format.
     * @return The method `updateCv` returns a `ResponseEntity` object. The response
     *         can be one of the
     *         following based on the conditions:
     */
    @PutMapping("/users/{userId}/cv")
    public ResponseEntity<?> updateCv(@PathVariable Long userId, @RequestBody Map<String, String> requestBody) {
        try {
            Optional<User> userOptional = userServices.findById(userId);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                String base64Cv = requestBody.get("cv");
                if (base64Cv == null || base64Cv.isEmpty()) {
                    return ResponseEntity.badRequest().body("No se proporcionó CV.");
                }
                byte[] cv = Base64.getDecoder().decode(base64Cv);
                user.setCv(cv); // Reemplaza el archivo anterior
                userServices.saveUsers(user);
                return ResponseEntity.ok("CV actualizado exitosamente.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al subir el CV: " + e.getMessage());
        }
    }

        /**
     * This function retrieves a user's CV in base64 format by their user ID.
     * 
     * @param userId The code snippet you provided is a Spring MVC controller method
     *               that retrieves a
     *               user's CV (curriculum vitae) by their user ID. It first
     *               attempts to find the user by ID using the
     *               `userServices.findById(userId)` method. If the user is found,
     *               it retrieves the CV byte array from
     *               the
     * @return The `getCv` method returns a `ResponseEntity` object. The response
     *         body can be a base64
     *         encoded CV file if found, a message indicating that the CV was not
     *         found, a message indicating that
     *         the user was not found, or an error message if an exception occurs
     *         during the process of obtaining
     *         the CV. The HTTP status code of the response varies depending on the
     *         outcome: 200 OK
     */
    @GetMapping("/users/{userId}/cv")
    public ResponseEntity<?> getCv(@PathVariable Long userId) {
        try {
            Optional<User> userOptional = userServices.findById(userId);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                byte[] cv = user.getCv();
                if (cv == null || cv.length == 0) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("CV no encontrado.");
                }
                String base64Cv = Base64.getEncoder().encodeToString(cv);
                return ResponseEntity.ok(base64Cv);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener el CV: " + e.getMessage());
        }
    }

    /**
     * This Java function updates a user's labor certificate by decoding a base64
     * string and saving it to
     * the user entity.
     * 
     * @param userId      The `userId` parameter in the `@PutMapping` annotation
     *                    represents the unique
     *                    identifier of the user whose labor certificate is being
     *                    updated. This identifier is used to locate
     *                    the specific user in the system based on the provided ID.
     * @param requestBody The `updateCertifLaboral` method is a PUT mapping that
     *                    updates the certificate of
     *                    employment for a specific user identified by their
     *                    `userId`. The method expects a base64 encoded
     *                    certificate of employment in the request body under the
     *                    key "certifLaboral".
     * @return The method `updateCertifLaboral` returns a `ResponseEntity` object.
     *         The response can be one
     *         of the following:
     */
    @PutMapping("/users/{userId}/certifLaboral")
    public ResponseEntity<?> updateCertifLaboral(@PathVariable Long userId,
            @RequestBody Map<String, String> requestBody) {
        try {
            Optional<User> userOptional = userServices.findById(userId);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                String base64CertifLaboral = requestBody.get("certifLaboral");
                if (base64CertifLaboral == null || base64CertifLaboral.isEmpty()) {
                    return ResponseEntity.badRequest().body("No se proporcionó certificado laboral.");
                }
                byte[] certifLaboral = Base64.getDecoder().decode(base64CertifLaboral);
                user.setCertifLaboral(certifLaboral); // Reemplaza el archivo anterior
                userServices.saveUsers(user);
                return ResponseEntity.ok("Certificado laboral actualizado exitosamente.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al subir el certificado laboral: " + e.getMessage());
        }
    }

        /**
     * This Java function retrieves a user's labor certificate in base64 format by
     * their user ID.
     * 
     * @param userId The code you provided is a Spring MVC controller method that
     *               retrieves a user's labor
     *               certificate as a Base64 encoded string. The method takes a
     *               userId as a path variable and attempts to
     *               find the corresponding user in the database. If the user is
     *               found and has a non-empty labor
     *               certificate, it encodes
     * @return The method `getCertifLaboral` returns a `ResponseEntity` object. The
     *         response body can be a
     *         base64 encoded string of the user's labor certificate if found, a
     *         message indicating that the labor
     *         certificate was not found, a message indicating that the user was not
     *         found, or an error message if
     *         an exception occurs during the process of obtaining the labor
     *         certificate. The HTTP status code of
     *         the
     */
    @GetMapping("/users/{userId}/certifLaboral")
    public ResponseEntity<?> getCertifLaboral(@PathVariable Long userId) {
        try {
            Optional<User> userOptional = userServices.findById(userId);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                byte[] certifLaboral = user.getCertifLaboral();
                if (certifLaboral == null || certifLaboral.length == 0) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Certificado laboral no encontrado.");
                }
                String base64CertifLaboral = Base64.getEncoder().encodeToString(certifLaboral);
                return ResponseEntity.ok(base64CertifLaboral);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener el certificado laboral: " + e.getMessage());
        }
    }

    /**
     * This Java function updates a user's academic certificate by decoding a Base64
     * string provided in the
     * request body.
     * 
     * @param userId      The `userId` parameter in the `@PutMapping` annotation
     *                    represents the unique
     *                    identifier of the user whose academic certificate is being
     *                    updated. This identifier is used to
     *                    locate the specific user in the system before updating
     *                    their academic certificate.
     * @param requestBody The `updateCertifAcademica` method is a PUT mapping that
     *                    allows updating the
     *                    academic certificate of a user identified by `userId`. The
     *                    method expects a base64 encoded academic
     *                    certificate in the request body under the key
     *                    "certifAcademica".
     * @return The method `updateCertifAcademica` returns a `ResponseEntity` object.
     *         The response can be
     *         one of the following based on the conditions:
     */
    @PutMapping("/users/{userId}/certifAcademica")
    public ResponseEntity<?> updateCertifAcademica(@PathVariable Long userId,
            @RequestBody Map<String, String> requestBody) {
        try {
            Optional<User> userOptional = userServices.findById(userId);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                String base64CertifAcademica = requestBody.get("certifAcademica");
                if (base64CertifAcademica == null || base64CertifAcademica.isEmpty()) {
                    return ResponseEntity.badRequest().body("No se proporcionó certificado académico.");
                }
                byte[] certifAcademica = Base64.getDecoder().decode(base64CertifAcademica);
                user.setCertifAcademica(certifAcademica); // Reemplaza el archivo anterior
                userServices.saveUsers(user);
                return ResponseEntity.ok("Certificado académico actualizado exitosamente.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al subir el certificado académico: " + e.getMessage());
        }
    }

        /**
     * This Java function retrieves and encodes a user's academic certificate as a
     * Base64 string.
     * 
     * @param userId The code you provided is a Spring MVC controller method that
     *               retrieves a user's
     *               academic certificate in base64 format. The method takes a
     *               userId path variable to identify the user
     *               whose certificate is being requested.
     * @return The `getCertifAcademica` method returns a `ResponseEntity` object.
     *         The response can be one
     *         of the following:
     */
    @GetMapping("/users/{userId}/certifAcademica")
    public ResponseEntity<?> getCertifAcademica(@PathVariable Long userId) {
        try {
            Optional<User> userOptional = userServices.findById(userId);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                byte[] certifAcademica = user.getCertifAcademica();
                if (certifAcademica == null || certifAcademica.length == 0) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Certificado académico no encontrado.");
                }
                String base64CertifAcademica = Base64.getEncoder().encodeToString(certifAcademica);
                return ResponseEntity.ok(base64CertifAcademica);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener el certificado académico: " + e.getMessage());
        }
    }

    /**
     * This Java function updates a user's EPS certificate by decoding a base64
     * string and saving it to the
     * user entity.
     * 
     * @param userId      The `userId` parameter in the `@PutMapping` annotation
     *                    represents the unique
     *                    identifier of the user whose EPS certificate is being
     *                    updated. This identifier is used to locate the
     *                    specific user in the system before updating their EPS
     *                    certificate.
     * @param requestBody The `updateCertifEps` method is a PUT mapping that updates
     *                    the certificate of the
     *                    EPS (Entidad Promotora de Salud) for a specific user
     *                    identified by `userId`.
     * @return The method `updateCertifEps` returns a `ResponseEntity` object. The
     *         response can be one of
     *         the following:
     */
    @PutMapping("/users/{userId}/certifEps")
    public ResponseEntity<?> updateCertifEps(@PathVariable Long userId, @RequestBody Map<String, String> requestBody) {
        try {
            Optional<User> userOptional = userServices.findById(userId);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                String base64CertifEps = requestBody.get("certifEps");
                if (base64CertifEps == null || base64CertifEps.isEmpty()) {
                    return ResponseEntity.badRequest().body("No se proporcionó certificado EPS.");
                }
                byte[] certifEps = Base64.getDecoder().decode(base64CertifEps);
                user.setCertifEps(certifEps); // Reemplaza el archivo anterior
                userServices.saveUsers(user);
                return ResponseEntity.ok("Certificado EPS actualizado exitosamente.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al subir el certificado EPS: " + e.getMessage());
        }
    }

        /**
     * This Java function retrieves and encodes a user's EPS certificate as a base64
     * string.
     * 
     * @param userId The code snippet you provided is a Spring MVC controller method
     *               that handles GET
     *               requests for retrieving a user's EPS certificate by userId. It
     *               first attempts to find the user by
     *               the provided userId, then checks if the user exists and has a
     *               non-empty EPS certificate. If the
     *               certificate is found, it encodes
     * @return The method `getCertifEps` returns a `ResponseEntity` object. The
     *         response can be one of the
     *         following:
     *         1. If the user with the specified userId is found and has a non-null
     *         certificate (certifEps), the
     *         method returns a response with status OK (200) and the base64 encoded
     *         certificate as the body.
     *         2. If the user is not found, the
     */
    @GetMapping("/users/{userId}/certifEps")
    public ResponseEntity<?> getCertifEps(@PathVariable Long userId) {
        try {
            Optional<User> userOptional = userServices.findById(userId);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                byte[] certifEps = user.getCertifEps();
                if (certifEps == null || certifEps.length == 0) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Certificado EPS no encontrado.");
                }
                String base64CertifEps = Base64.getEncoder().encodeToString(certifEps);
                return ResponseEntity.ok(base64CertifEps);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener el certificado EPS: " + e.getMessage());
        }
    }

    /**
     * This Java function updates a user's pension fund certificate by decoding a
     * base64 string and saving
     * it to the user object.
     * 
     * @param userId      The `userId` parameter in the `updateCertifFondoPension`
     *                    method represents the unique
     *                    identifier of the user for whom you are updating the
     *                    certificate of the pension fund. This parameter
     *                    is part of the path in the URL mapping of the PUT request.
     *                    It is used to locate the specific user
     * @param requestBody The `updateCertifFondoPension` method is a PUT mapping
     *                    that updates the
     *                    certificate of the pension fund for a specific user
     *                    identified by `userId`. The method expects a
     *                    base64 encoded string representing the new certificate in
     *                    the request body under the key
     *                    "certifFondoPension".
     * @return The method `updateCertifFondoPension` returns a `ResponseEntity`
     *         object. The response can be
     *         one of the following based on the conditions:
     */
    @PutMapping("/users/{userId}/certifFondoPension")
    public ResponseEntity<?> updateCertifFondoPension(@PathVariable Long userId,
            @RequestBody Map<String, String> requestBody) {
        try {
            Optional<User> userOptional = userServices.findById(userId);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                String base64CertifFondoPension = requestBody.get("certifFondoPension");
                if (base64CertifFondoPension == null || base64CertifFondoPension.isEmpty()) {
                    return ResponseEntity.badRequest().body("No se proporcionó certificado fondo de pensión.");
                }
                byte[] certifFondoPension = Base64.getDecoder().decode(base64CertifFondoPension);
                user.setCertifFondoPension(certifFondoPension); // Reemplaza el archivo anterior
                userServices.saveUsers(user);
                return ResponseEntity.ok("Certificado fondo de pensión actualizado exitosamente.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al subir el certificado fondo de pensión: " + e.getMessage());
        }
    }

        /**
     * This Java function retrieves a user's pension fund certificate in base64
     * format if available,
     * handling errors appropriately.
     * 
     * @param userId The code snippet you provided is a Spring `@GetMapping` method
     *               that retrieves a user's
     *               certificate for a pension fund based on the `userId` path
     *               variable. If the user is found and has a
     *               certificate, it is returned as a Base64 encoded string in the
     *               response entity. If the user or
     * @return The `getCertifFondoPension` method returns a `ResponseEntity` object.
     *         The response can be
     *         one of the following:
     */
    @GetMapping("/users/{userId}/certifFondoPension")
    public ResponseEntity<?> getCertifFondoPension(@PathVariable Long userId) {
        try {
            Optional<User> userOptional = userServices.findById(userId);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                byte[] certifFondoPension = user.getCertifFondoPension();
                if (certifFondoPension == null || certifFondoPension.length == 0) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body("Certificado fondo de pensión no encontrado.");
                }
                String base64CertifFondoPension = Base64.getEncoder().encodeToString(certifFondoPension);
                return ResponseEntity.ok(base64CertifFondoPension);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener el certificado fondo de pensión: " + e.getMessage());
        }
    }

    /**
     * This Java function updates a user's ARL certificate by decoding a base64
     * string and saving it to the
     * user entity.
     * 
     * @param userId      The `userId` parameter in the `@PutMapping` annotation
     *                    represents the unique
     *                    identifier of the user whose ARL certificate is being
     *                    updated. This endpoint is designed to handle
     *                    PUT requests to update the ARL certificate for a specific
     *                    user identified by their `userId`.
     * @param requestBody The `updateCertifArl` method is a PUT mapping that updates
     *                    the ARL certificate
     *                    for a specific user identified by `userId`. The method
     *                    expects a JSON request body with a key
     *                    "certifArl" containing the base64 encoded ARL certificate.
     * @return The method `updateCertifArl` returns a `ResponseEntity` object. The
     *         response can be one of
     *         the following:
     */
    @PutMapping("/users/{userId}/certifArl")
    public ResponseEntity<?> updateCertifArl(@PathVariable Long userId, @RequestBody Map<String, String> requestBody) {
        try {
            Optional<User> userOptional = userServices.findById(userId);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                String base64CertifArl = requestBody.get("certifArl");
                if (base64CertifArl == null || base64CertifArl.isEmpty()) {
                    return ResponseEntity.badRequest().body("No se proporcionó certificado ARL.");
                }
                byte[] certifArl = Base64.getDecoder().decode(base64CertifArl);
                user.setCertifArl(certifArl); // Reemplaza el archivo anterior
                userServices.saveUsers(user);
                return ResponseEntity.ok("Certificado ARL actualizado exitosamente.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al subir el certificado ARL: " + e.getMessage());
        }
    }

        /**
     * This Java function retrieves and encodes a user's ARL certificate as a base64
     * string.
     * 
     * @param userId The code snippet you provided is a Spring MVC controller method
     *               that handles GET
     *               requests to retrieve a user's ARL certificate. It takes the
     *               userId as a path variable and attempts
     *               to fetch the user's ARL certificate from the database.
     * @return The `getCertifArl` method returns a `ResponseEntity` object. The
     *         response body can be a
     *         base64 encoded string of the user's ARL certificate if found, or an
     *         error message if the user or
     *         certificate is not found, or if there is an internal server error.
     */
    @GetMapping("/users/{userId}/certifArl")
    public ResponseEntity<?> getCertifArl(@PathVariable Long userId) {
        try {
            Optional<User> userOptional = userServices.findById(userId);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                byte[] certifArl = user.getCertifArl();
                if (certifArl == null || certifArl.length == 0) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Certificado ARL no encontrado.");
                }
                String base64CertifArl = Base64.getEncoder().encodeToString(certifArl);
                return ResponseEntity.ok(base64CertifArl);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener el certificado ARL: " + e.getMessage());
        }
    }

    /**
     * This Java function updates a user's bank certificate by decoding a base64
     * string provided in the
     * request body.
     * 
     * @param userId      The `userId` parameter in the `@PutMapping` annotation
     *                    represents the unique
     *                    identifier of the user whose banking certificate is being
     *                    updated. This identifier is used to locate
     *                    the specific user in the system based on the provided ID.
     * @param requestBody The `updateCertifBancario` method you provided is a Spring
     *                    `@PutMapping` endpoint
     *                    that updates the certificate bancario (bank certificate)
     *                    for a specific user identified by `userId`.
     *                    The method expects a JSON request body with a key
     *                    "certifBancario" containing the base
     * @return The method `updateCertifBancario` returns a `ResponseEntity` object.
     *         The response can be one
     *         of the following:
     */
    @PutMapping("/users/{userId}/certifBancario")
    public ResponseEntity<?> updateCertifBancario(@PathVariable Long userId,
            @RequestBody Map<String, String> requestBody) {
        try {
            Optional<User> userOptional = userServices.findById(userId);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                String base64CertifBancario = requestBody.get("certifBancario");
                if (base64CertifBancario == null || base64CertifBancario.isEmpty()) {
                    return ResponseEntity.badRequest().body("No se proporcionó certificado bancario.");
                }
                byte[] certifBancario = Base64.getDecoder().decode(base64CertifBancario);
                user.setCertifBancario(certifBancario); // Reemplaza el archivo anterior
                userServices.saveUsers(user);
                return ResponseEntity.ok("Certificado bancario actualizado exitosamente.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al subir el certificado bancario: " + e.getMessage());
        }
    }

    /**
     * This Java function retrieves a user's banking certificate in base64 format if
     * it exists, handling
     * errors appropriately.
     * 
     * @param userId The code snippet you provided is a Spring MVC controller method
     *               that handles GET
     *               requests for retrieving a user's banking certificate based on
     *               the user's ID. It checks if the user
     *               exists, retrieves the banking certificate, encodes it to
     *               Base64, and returns it in the response
     *               entity.
     * @return The `getCertifBancario` method returns a `ResponseEntity` object. The
     *         response can be one of
     *         the following:
     */
    @GetMapping("/users/{userId}/certifBancario")
    public ResponseEntity<?> getCertifBancario(@PathVariable Long userId) {
        try {
            Optional<User> userOptional = userServices.findById(userId);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                byte[] certifBancario = user.getCertifBancario();
                if (certifBancario == null || certifBancario.length == 0) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Certificado bancario no encontrado.");
                }
                String base64CertifBancario = Base64.getEncoder().encodeToString(certifBancario);
                return ResponseEntity.ok(base64CertifBancario);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener el certificado bancario: " + e.getMessage());
        }
    }

    /**
     * This Java function updates a user's disciplinary background certificate by
     * decoding a Base64 string
     * provided in the request body.
     * 
     * @param userId      The `userId` parameter in the
     *                    `updateCertifAntecedDisciplinario` method represents the
     *                    unique identifier of the user for whom you are updating
     *                    the certificate of disciplinary records.
     *                    This parameter is part of the path in the URL mapping for
     *                    the PUT request. It is used to locate the
     *                    specific
     * @param requestBody The `updateCertifAntecedDisciplinario` method is a PUT
     *                    mapping that updates the
     *                    "certifAntecedDisciplinario" (disciplinary background
     *                    certificate) for a specific user identified by
     *                    the `userId`. The method expects a base64 encoded string
     *                    representing the new certificate in the
     *                    request
     * @return The method `updateCertifAntecedDisciplinario` returns a
     *         `ResponseEntity` object. The
     *         response can be one of the following:
     */
    @PutMapping("/users/{userId}/certifAntecedDisciplinario")
    public ResponseEntity<?> updateCertifAntecedDisciplinario(@PathVariable Long userId,
            @RequestBody Map<String, String> requestBody) {
        try {
            Optional<User> userOptional = userServices.findById(userId);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                String base64CertifAntecedDisciplinario = requestBody.get("certifAntecedDisciplinario");
                if (base64CertifAntecedDisciplinario == null || base64CertifAntecedDisciplinario.isEmpty()) {
                    return ResponseEntity.badRequest()
                            .body("No se proporcionó certificado antecedentes disciplinarios.");
                }
                byte[] certifAntecedDisciplinario = Base64.getDecoder().decode(base64CertifAntecedDisciplinario);
                user.setCertifAntecedDisciplinario(certifAntecedDisciplinario); // Reemplaza el archivo anterior
                userServices.saveUsers(user);
                return ResponseEntity.ok("Certificado antecedentes disciplinarios actualizado exitosamente.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al subir el certificado antecedentes disciplinarios: " + e.getMessage());
        }
    }

    
    /**
     * This Java function retrieves a user's disciplinary background certificate in
     * base64 format by user
     * ID.
     * 
     * @param userId The code you provided is a Spring MVC controller method that
     *               retrieves a user's
     *               disciplinary background certificate in base64 format. The
     *               method takes a userId path variable,
     *               retrieves the user from the database using the
     *               userServices.findById(userId) method, and then checks
     *               if the user exists. If the user exists and has
     * @return The method `getCertifAntecedDisciplinario` returns a `ResponseEntity`
     *         object. The response
     *         body can be a base64 encoded string of the certificate of
     *         disciplinary background if found, a
     *         message indicating that the certificate was not found, a message
     *         indicating that the user was not
     *         found, or an error message if an exception occurs during the process.
     *         The HTTP status codes used are
     *         `200
     */
    @GetMapping("/users/{userId}/certifAntecedDisciplinario")
    public ResponseEntity<?> getCertifAntecedDisciplinario(@PathVariable Long userId) {
        try {
            Optional<User> userOptional = userServices.findById(userId);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                byte[] certifAntecedDisciplinario = user.getCertifAntecedDisciplinario();
                if (certifAntecedDisciplinario == null || certifAntecedDisciplinario.length == 0) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body("Certificado antecedentes disciplinarios no encontrado.");
                }
                String base64CertifAntecedDisciplinario = Base64.getEncoder()
                        .encodeToString(certifAntecedDisciplinario);
                return ResponseEntity.ok(base64CertifAntecedDisciplinario);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener el certificado antecedentes disciplinarios: " + e.getMessage());
        }
    }


    /**
     * This Java function updates a user's certificate of criminal records based on
     * a Base64 encoded input.
     * 
     * @param userId      The `userId` parameter in the `@PutMapping` annotation
     *                    represents the unique
     *                    identifier of the user whose certificate of criminal
     *                    records (certificado antecedentes fiscales) is
     *                    being updated. This endpoint is designed to update the
     *                    certificate of criminal records for a
     *                    specific user identified by their `userId`.
     * @param requestBody The `updateCertifAntecedFiscales` method is a PUT mapping
     *                    that updates the
     *                    certificate of criminal records for a specific user
     *                    identified by `userId`. The method expects a
     *                    base64 encoded certificate in the request body under the
     *                    key "certifAntecedFiscales".
     * @return The method `updateCertifAntecedFiscales` returns a `ResponseEntity`
     *         object. The response can
     *         be one of the following:
     */
    @PutMapping("/users/{userId}/certifAntecedFiscales")
    public ResponseEntity<?> updateCertifAntecedFiscales(@PathVariable Long userId,
            @RequestBody Map<String, String> requestBody) {
        try {
            Optional<User> userOptional = userServices.findById(userId);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                String base64CertifAntecedFiscales = requestBody.get("certifAntecedFiscales");
                if (base64CertifAntecedFiscales == null || base64CertifAntecedFiscales.isEmpty()) {
                    return ResponseEntity.badRequest().body("No se proporcionó certificado antecedentes fiscales.");
                }
                byte[] certifAntecedFiscales = Base64.getDecoder().decode(base64CertifAntecedFiscales);
                user.setCertifAntecedFiscales(certifAntecedFiscales); // Reemplaza el archivo anterior
                userServices.saveUsers(user);
                return ResponseEntity.ok("Certificado antecedentes fiscales actualizado exitosamente.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al subir el certificado antecedentes fiscales: " + e.getMessage());
        }
    }

    /**
     * This Java function retrieves a user's certificate of fiscal background in
     * base64 format by user ID.
     * 
     * @param userId The code you provided is a Spring MVC controller method that
     *               handles GET requests to
     *               retrieve a user's certificate of criminal records (certificado
     *               de antecedentes fiscales) in base64
     *               format. It first tries to find the user by the provided userId,
     *               then checks if the certificate
     *               exists and returns it in
     * @return The `getCertifAntecedFiscales` method returns a `ResponseEntity`
     *         object. The response body
     *         can be a base64 encoded string of the user's certificate of criminal
     *         records (antecedentes
     *         fiscales), or an error message if the user or the certificate is not
     *         found, or if there is an
     *         internal server error. The HTTP status code of the response varies
     *         depending on the outcome
     */
    @GetMapping("/users/{userId}/certifAntecedFiscales")
    public ResponseEntity<?> getCertifAntecedFiscales(@PathVariable Long userId) {
        try {
            Optional<User> userOptional = userServices.findById(userId);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                byte[] certifAntecedFiscales = user.getCertifAntecedFiscales();
                if (certifAntecedFiscales == null || certifAntecedFiscales.length == 0) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body("Certificado antecedentes fiscales no encontrado.");
                }
                String base64CertifAntecedFiscales = Base64.getEncoder().encodeToString(certifAntecedFiscales);
                return ResponseEntity.ok(base64CertifAntecedFiscales);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener el certificado antecedentes fiscales: " + e.getMessage());
        }
    }

    /**
     * This Java function updates a user's criminal record certificate by decoding a
     * Base64 string and
     * saving it to the user entity.
     * 
     * @param userId      The `userId` parameter in the `@PutMapping` annotation
     *                    represents the unique
     *                    identifier of the user whose certificate of criminal
     *                    records (certificado de antecedentes penales)
     *                    is being updated. This endpoint allows the user to update
     *                    their certificate of criminal records by
     *                    providing a base64 encoded string of the
     * @param requestBody The `updateCertifAntecedPenales` method is a PUT mapping
     *                    that updates the
     *                    certificate of criminal records for a specific user
     *                    identified by `userId`. The method expects a
     *                    base64 encoded string representing the new certificate in
     *                    the request body under the key
     *                    "certifAntecedPenales".
     * @return The method `updateCertifAntecedPenales` returns a `ResponseEntity`
     *         object. The response can
     *         be one of the following:
     */
    @PutMapping("/users/{userId}/certifAntecedPenales")
    public ResponseEntity<?> updateCertifAntecedPenales(@PathVariable Long userId,
            @RequestBody Map<String, String> requestBody) {
        try {
            Optional<User> userOptional = userServices.findById(userId);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                String base64CertifAntecedPenales = requestBody.get("certifAntecedPenales");
                if (base64CertifAntecedPenales == null || base64CertifAntecedPenales.isEmpty()) {
                    return ResponseEntity.badRequest().body("No se proporcionó certificado antecedentes penales.");
                }
                byte[] certifAntecedPenales = Base64.getDecoder().decode(base64CertifAntecedPenales);
                user.setCertifAntecedPenales(certifAntecedPenales); // Reemplaza el archivo anterior
                userServices.saveUsers(user);
                return ResponseEntity.ok("Certificado antecedentes penales actualizado exitosamente.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al subir el certificado antecedentes penales: " + e.getMessage());
        }
    }

    /**
     * This Java function retrieves a user's criminal record certificate in base64
     * format by user ID.
     * 
     * @param userId The code you provided is a Spring MVC controller method that
     *               retrieves a user's
     *               certificate of criminal records (certifAntecedPenales) by their
     *               userId. It first tries to find the
     *               user by the provided userId, then checks if the certificate
     *               exists. If the certificate is found, it
     *               encodes it
     * @return The method `getCertifAntecedPenales` returns a `ResponseEntity`
     *         object. The response body
     *         can be a base64 encoded string of the user's certificate of criminal
     *         record (antecedentes penales)
     *         if found, or an error message if the user or the certificate is not
     *         found, or if there is an
     *         internal server error. The HTTP status code of the response varies
     *         depending on
     */
    @GetMapping("/users/{userId}/certifAntecedPenales")
    public ResponseEntity<?> getCertifAntecedPenales(@PathVariable Long userId) {
        try {
            Optional<User> userOptional = userServices.findById(userId);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                byte[] certifAntecedPenales = user.getCertifAntecedPenales();
                if (certifAntecedPenales == null || certifAntecedPenales.length == 0) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body("Certificado antecedentes penales no encontrado.");
                }
                String base64CertifAntecedPenales = Base64.getEncoder().encodeToString(certifAntecedPenales);
                return ResponseEntity.ok(base64CertifAntecedPenales);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener el certificado antecedentes penales: " + e.getMessage());
        }
    }

    /**
     * This Java function updates a user's military ID document by decoding a Base64
     * string and saving it
     * to the user entity.
     * 
     * @param userId      The `userId` parameter in the `updateLibretaMilitar`
     *                    method represents the unique
     *                    identifier of the user whose libreta militar (military
     *                    booklet) is being updated. This parameter is
     *                    extracted from the path of the URL where the PUT request
     *                    is made. The method then attempts to find
     *                    the
     * @param requestBody The `updateLibretaMilitar` method is a PUT mapping that
     *                    updates the "libreta
     *                    militar" (military ID) of a user identified by `userId`.
     *                    The method expects a base64 encoded string
     *                    representing the new "libreta militar" in the request body
     *                    under the key "lib
     * @return The `updateLibretaMilitar` method returns a `ResponseEntity` object.
     *         The response can be one
     *         of the following based on the conditions:
     */
    @PutMapping("/users/{userId}/libretaMilitar")
    public ResponseEntity<?> updateLibretaMilitar(@PathVariable Long userId,
            @RequestBody Map<String, String> requestBody) {
        try {
            Optional<User> userOptional = userServices.findById(userId);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                String base64LibretaMilitar = requestBody.get("libretaMilitar");
                if (base64LibretaMilitar == null || base64LibretaMilitar.isEmpty()) {
                    return ResponseEntity.badRequest().body("No se proporcionó libreta militar.");
                }
                byte[] libretaMilitar = Base64.getDecoder().decode(base64LibretaMilitar);
                user.setLibretaMilitar(libretaMilitar); // Reemplaza el archivo anterior
                userServices.saveUsers(user);
                return ResponseEntity.ok("Libreta militar actualizada exitosamente.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al subir la libreta militar: " + e.getMessage());
        }
    }

    /**
     * This Java function retrieves a user's military ID document in base64 format
     * by their user ID.
     * 
     * @param userId The code snippet you provided is a Spring MVC controller method
     *               that retrieves a
     *               user's military ID card (libreta militar) in base64 format. It
     *               first tries to find the user by the
     *               provided `userId`, then checks if the user exists and has a
     *               military ID card associated with them.
     *               If the
     * @return The `getLibretaMilitar` method returns a `ResponseEntity` object. The
     *         response body can be a
     *         base64 encoded string representing the user's military ID card
     *         (libreta militar) if found, or an
     *         error message if the user or the military ID card is not found, or if
     *         there is an internal server
     *         error. The HTTP status code of the response varies depending on the
     *         outcome
     */
    @GetMapping("/users/{userId}/libretaMilitar")
    public ResponseEntity<?> getLibretaMilitar(@PathVariable Long userId) {
        try {
            Optional<User> userOptional = userServices.findById(userId);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                byte[] libretaMilitar = user.getLibretaMilitar();
                if (libretaMilitar == null || libretaMilitar.length == 0) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Libreta militar no encontrada.");
                }
                String base64LibretaMilitar = Base64.getEncoder().encodeToString(libretaMilitar);
                return ResponseEntity.ok(base64LibretaMilitar);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener la libreta militar: " + e.getMessage());
        }
    }

    /**
     * This Java function updates a user's occupational medical certificate by
     * decoding a Base64 string
     * provided in the request body.
     * 
     * @param userId      The `userId` parameter in the `updateCertifMedOcupacional`
     *                    method represents the
     *                    unique identifier of the user for whom the occupational
     *                    medical certificate is being updated. This
     *                    parameter is part of the path in the endpoint URL
     *                    `/users/{userId}/certifMedOcupacional`, and it is
     * @param requestBody The `updateCertifMedOcupacional` method is a PUT mapping
     *                    that updates the medical
     *                    occupational certificate for a specific user identified by
     *                    `userId`. The method expects a base64
     *                    encoded string representing the new certificate in the
     *                    request body under the key
     *                    "certifMedOcupacional".
     * @return The method `updateCertifMedOcupacional` returns a `ResponseEntity`
     *         object. The response can
     *         be one of the following:
     */
    @PutMapping("/users/{userId}/certifMedOcupacional")
    public ResponseEntity<?> updateCertifMedOcupacional(@PathVariable Long userId,
            @RequestBody Map<String, String> requestBody) {
        try {
            Optional<User> userOptional = userServices.findById(userId);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                String base64CertifMedOcupacional = requestBody.get("certifMedOcupacional");
                if (base64CertifMedOcupacional == null || base64CertifMedOcupacional.isEmpty()) {
                    return ResponseEntity.badRequest().body("No se proporcionó certificado médico ocupacional.");
                }
                byte[] certifMedOcupacional = Base64.getDecoder().decode(base64CertifMedOcupacional);
                user.setCertifMedOcupacional(certifMedOcupacional); // Reemplaza el archivo anterior
                userServices.saveUsers(user);
                return ResponseEntity.ok("Certificado médico ocupacional actualizado exitosamente.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al subir el certificado médico ocupacional: " + e.getMessage());
        }
    }

    /**
     * This Java function retrieves and encodes a user's occupational medical
     * certificate as a base64
     * string.
     * 
     * @param userId The code snippet you provided is a Spring MVC controller method
     *               that handles GET
     *               requests for retrieving a user's occupational medical
     *               certificate. It takes the userId as a path
     *               variable and attempts to fetch the certificate from the user
     *               entity.
     * @return The `getCertifMedOcupacional` method returns a `ResponseEntity`
     *         object. The response can be
     *         one of the following:
     */
    @GetMapping("/users/{userId}/certifMedOcupacional")
    public ResponseEntity<?> getCertifMedOcupacional(@PathVariable Long userId) {
        try {
            Optional<User> userOptional = userServices.findById(userId);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                byte[] certifMedOcupacional = user.getCertifMedOcupacional();
                if (certifMedOcupacional == null || certifMedOcupacional.length == 0) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body("Certificado médico ocupacional no encontrado.");
                }
                String base64CertifMedOcupacional = Base64.getEncoder().encodeToString(certifMedOcupacional);
                return ResponseEntity.ok(base64CertifMedOcupacional);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener el certificado médico ocupacional: " + e.getMessage());
        }
    }

    /**
     * This Java function updates a user's personal references by decoding a base64
     * string and saving it to
     * the user entity.
     * 
     * @param userId      The `userId` parameter in the `@PutMapping` annotation
     *                    represents the unique
     *                    identifier of the user whose personal references are being
     *                    updated. This endpoint is designed to
     *                    update the personal references of a user identified by the
     *                    `userId` path variable.
     * @param requestBody The `updateReferPersonales` method is a PUT mapping that
     *                    updates the personal
     *                    references of a user identified by `userId`. The method
     *                    expects a base64 encoded string representing
     *                    the personal references in the request body under the key
     *                    "referPersonales".
     * @return The method `updateReferPersonales` returns a `ResponseEntity` object.
     *         The response can be
     *         one of the following:
     */
    @PutMapping("/users/{userId}/referPersonales")
    public ResponseEntity<?> updateReferPersonales(@PathVariable Long userId,
            @RequestBody Map<String, String> requestBody) {
        try {
            Optional<User> userOptional = userServices.findById(userId);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                String base64ReferPersonales = requestBody.get("referPersonales");
                if (base64ReferPersonales == null || base64ReferPersonales.isEmpty()) {
                    return ResponseEntity.badRequest().body("No se proporcionó referencias personales.");
                }
                byte[] referPersonales = Base64.getDecoder().decode(base64ReferPersonales);
                user.setReferPersonales(referPersonales); // Reemplaza el archivo anterior
                userServices.saveUsers(user);
                return ResponseEntity.ok("Referencias personales actualizadas exitosamente.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al subir las referencias personales: " + e.getMessage());
        }
    }

    /**
     * This Java function retrieves personal references for a user by their ID,
     * encoding them in Base64 if
     * found.
     * 
     * @param userId The code you provided is a Spring MVC controller method that
     *               retrieves personal
     *               references for a user based on the user's ID. The method first
     *               attempts to find the user by ID using
     *               the `userServices.findById(userId)` method. If the user is
     *               found, it retrieves the personal
     *               references stored as a byte array
     * @return The method is returning a ResponseEntity with either the base64
     *         encoded personal references
     *         of a user if found, a message indicating that the personal references
     *         were not found, a message
     *         indicating that the user was not found, or an error message if an
     *         exception occurs while trying to
     *         retrieve the personal references.
     */
    @GetMapping("/users/{userId}/referPersonales")
    public ResponseEntity<?> getReferPersonales(@PathVariable Long userId) {
        try {
            Optional<User> userOptional = userServices.findById(userId);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                byte[] referPersonales = user.getReferPersonales();
                if (referPersonales == null || referPersonales.length == 0) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Referencias personales no encontradas.");
                }
                String base64ReferPersonales = Base64.getEncoder().encodeToString(referPersonales);
                return ResponseEntity.ok(base64ReferPersonales);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener las referencias personales: " + e.getMessage());
        }
    }

}