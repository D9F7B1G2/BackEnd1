package com.example.demo.Services;

import com.example.demo.Dto.RegisterDto;
import com.example.demo.Model.Entity.User;
import com.example.demo.Model.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class UserServices {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * La función `validateUser` verifica si existe un usuario con el correo
     * electrónico proporcionado
     * y si la contraseña ingresada coincide con la contraseña encriptada
     * almacenada.
     * 
     * @param email    Una cadena (`String`) que representa el correo electrónico
     *                 del usuario que intenta iniciar sesión.
     * @param password El parámetro `password` en el método `validateUser` es la
     *                 contraseña ingresada por el
     *                 usuario que intenta iniciar sesión. Esta contraseña se
     *                 comparará con la almacenada en la base de datos
     *                 después de ser encriptada utilizando un codificador de
     *                 contraseñas (en este caso, parece ser `passwordEncoder`).
     * @return El método `validateUser` devuelve un objeto `User` si el correo
     *         electrónico y la contraseña
     *         proporcionados coinciden con un usuario en el repositorio y la
     *         contraseña es correcta. Si no se encuentra
     *         un usuario que coincida o la contraseña es incorrecta, devuelve
     *         `null`.
     */

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

    /**
     * La función `registerUser` registra un nuevo usuario verificando si ya existen
     * el correo electrónico
     * o el número de documento, creando una nueva entidad de usuario, encriptando
     * la contraseña y
     * guardándola en el repositorio.
     * 
     * @param registerDto El parámetro `registerDto` en el método `registerUser` es
     *                    de tipo `RegisterDto`,
     *                    el cual probablemente contiene la información de registro
     *                    del usuario como el correo electrónico,
     *                    la contraseña y el número de documento. Este método se
     *                    encarga de registrar un nuevo usuario
     *                    verificando si el correo o el número de documento ya
     *                    existen en la base de datos.
     * @return Si el correo electrónico o el número de documento ya existen en la
     *         base de datos, se devuelve `null`.
     *         De lo contrario, se crea un nuevo objeto `User`, se guarda en la base
     *         de datos y se devuelve.
     */

    public User registerUser(RegisterDto registerDto) {
        if (userRepository.findByEmail(registerDto.getEmailPersonal()).isPresent() ||
                userRepository.findByNumeroDocumento(registerDto.getNumeroDocumento()).isPresent()) {
            return null;
        }
        User user = new User();
        user.setEmailPersonal(registerDto.getEmailPersonal());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword())); // Encriptamos la contraseña
        user.setNumeroDocumento(registerDto.getNumeroDocumento());
        return userRepository.save(user);
    }

    /**
     * La función `getAllUsers()` devuelve una lista de todos los usuarios
     * utilizando el repositorio para obtenerlos.
     * 
     * @return Se devuelve una lista (`List`) de objetos `User`.
     */
    public List<User> getAllUsers() {
        return userRepository.findAll(); // Usa el repositorio para obtener todos los usuarios
    }

    /**
     * La función `findById(Long userId)` devuelve un objeto `Optional<User>` al
     * buscar un usuario
     * con el `userId` especificado en el `userRepository`.
     * 
     * @param userId El parámetro `userId` es de tipo `Long` y representa el
     *               identificador único
     *               del usuario en el sistema.
     * @return Se devuelve un objeto `Optional<User>` que puede contener el usuario
     *         encontrado o estar vacío
     *         si no existe un usuario con ese ID.
     */
    public Optional<User> findById(Long userId) {
        return userRepository.findById(userId);
    }

    /**
     * La función `saveUsers` guarda un objeto de tipo `User` utilizando el
     * `userRepository` en Java.
     * 
     * @param user El parámetro `user` es un objeto de tipo `User` que contiene
     *             información sobre un usuario,
     *             como su nombre, correo electrónico y otros datos relevantes. Este
     *             objeto se guarda en el repositorio
     *             utilizando el método `save` proporcionado por el
     *             `userRepository`.
     */
    public void saveUsers(User user) {
        userRepository.save(user);
    }

    /**
     * La función `updatePassword` en Java verifica la existencia del usuario,
     * comprueba la contraseña anterior,
     * valida la nueva contraseña y actualiza la contraseña en la base de datos si
     * se cumplen todas las condiciones.
     * 
     * @param userId          El parámetro `userId` es el identificador único del
     *                        usuario cuya contraseña se está actualizando.
     *                        Se utiliza para recuperar al usuario de la base de
     *                        datos antes de realizar la actualización.
     * @param oldPassword     El parámetro `oldPassword` es la contraseña actual del
     *                        usuario que desea cambiar.
     *                        Se utiliza para verificar que el usuario que está
     *                        ingresando la nueva contraseña es efectivamente el
     *                        titular
     *                        de la cuenta y tiene autoridad para realizar el
     *                        cambio.
     * @param newPassword     El parámetro `newPassword` en el método
     *                        `updatePassword` representa la nueva contraseña
     *                        que el usuario desea establecer para su cuenta. Esta
     *                        nueva contraseña reemplazará la actual después de ser
     *                        validada exitosamente y cumplir con los criterios
     *                        especificados en el método.
     * @param confirmPassword El parámetro `confirmPassword` en el método
     *                        `updatePassword` es una cadena (`String`)
     *                        que representa la confirmación de la nueva contraseña
     *                        ingresada por el usuario. Este parámetro se utiliza
     *                        para
     *                        asegurarse de que el usuario haya escrito
     *                        correctamente la nueva contraseña, comparándola con el
     *                        parámetro
     *                        `newPassword` antes de continuar.
     */
    public void updatePassword(Long userId, String oldPassword, String newPassword, String confirmPassword) {
        // Verificar si el usuario existe
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado en la BD."));
        // Verificar que la contraseña antigua coincida
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new IllegalArgumentException("La contraseña anterior es incorrecta.");
        }
        // Validar que la nueva contraseña no sea igual a la antigua
        if (passwordEncoder.matches(newPassword, user.getPassword())) {
            throw new IllegalArgumentException("La nueva contraseña no puede ser igual a la anterior.");
        }
        // Verificar longitud de la nueva contraseña
        if (newPassword.length() < 8) {
            throw new IllegalArgumentException("La nueva contraseña debe tener al menos 8 caracteres.");
        }
        // Validar que la nueva contraseña y la confirmación coincidan
        if (!newPassword.equals(confirmPassword)) {
            throw new IllegalArgumentException("La nueva contraseña y la confirmación no coinciden.");
        }
        // Encriptar la nueva contraseña y guardarla en la base de datos
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        System.out.println("Contraseña actualizada correctamente para el usuario: " + user.getEmailPersonal());
    }

    /**
     * La función `findByEmail` busca un usuario por su correo electrónico en el
     * repositorio y devuelve
     * el usuario si lo encuentra, o `null` si no lo encuentra.
     * 
     * @param email El método `findByEmail` se utiliza para buscar un usuario en el
     *              repositorio a partir
     *              de su dirección de correo electrónico. El método recibe una
     *              dirección de correo como parámetro y
     *              devuelve el usuario correspondiente si se encuentra, o `null` si
     *              no existe un usuario con ese correo.
     * @return El método `findByEmail` devuelve un objeto `User` si se encuentra un
     *         usuario con el correo
     *         electrónico especificado en el repositorio. Si no se encuentra ningún
     *         usuario con ese correo, devuelve `null`.
     */
    public User findByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.orElse(null); // Devuelve el usuario si lo encuentra, o null si no existe
    }

    /**
     * La función `updateUserImage` actualiza la imagen de perfil de un usuario en
     * una aplicación Java,
     * decodificando una cadena de imagen en base64 y guardándola en la entidad del
     * usuario.
     * 
     * @param userId      El parámetro `userId` es un valor de tipo `Long` que
     *                    representa el identificador único
     *                    del usuario cuya imagen se está actualizando.
     * @param base64Image Una cadena codificada en base64 que representa una imagen,
     *                    la cual será decodificada
     *                    y guardada como imagen de perfil del usuario con el
     *                    `userId` especificado.
     */
    public void updateUserImage(Long userId, String base64Image) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            byte[] imageBytes = Base64.getDecoder().decode(base64Image);
            user.setImgProfile(imageBytes);
            userRepository.save(user);
        } else {
            throw new RuntimeException("Usuario no encontrado");
        }
    }

    /**
     * The function `updateCedulaPdf` updates the cedula PDF for a user with the
     * specified userId
     * in a Java application.
     * 
     * @param userId    The `userId` parameter is the unique identifier of the user
     *                  whose Cedula PDF
     *                  is being updated.
     * @param cedulaPdf The `updateCedulaPdf` method takes in a `userId` of type
     *                  Long and a
     *                  `cedulaPdf` of type byte array. The method updates the
     *                  `cedulaPdf` for the user with the
     *                  specified `userId` in the database. If the user is not found
     *                  based on the
     */
    public void updateCedulaPdf(Long userId, byte[] cedulaPdf) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setCedulaPdf(cedulaPdf);
            userRepository.save(user);
        } else {
            throw new RuntimeException("Usuario no encontrado");
        }
    }

    /**
     * The function `updateCv` updates the CV (curriculum vitae) of a user in the
     * database based on
     * the provided user ID and CV data.
     * 
     * @param userId The `userId` parameter is the unique identifier of the user
     *               whose CV
     *               (curriculum vitae) needs to be updated.
     * @param cv     The `updateCv` method takes in a `userId` of type Long and a
     *               `cv` of type byte
     *               array. The method updates the CV (curriculum vitae) of the user
     *               with the specified `userId`
     *               in the database. If the user is found in the repository, the CV
     */
    public void updateCv(Long userId, byte[] cv) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setCv(cv);
            userRepository.save(user);
        } else {
            throw new RuntimeException("Usuario no encontrado");
        }
    }

    /**
     * The function updates the certification of a user identified by their ID in a
     * Java
     * application.
     * 
     * @param userId        The `userId` parameter is the unique identifier of the
     *                      user for whom the labor
     *                      certificate is being updated.
     * @param certifLaboral The `certifLaboral` parameter in the
     *                      `updateCertifLaboral` method is a
     *                      byte array that represents the user's labor
     *                      certification document. This method updates the
     *                      labor certification document for a specific user
     *                      identified by their `userId`. If the user
     *                      is found in the repository, the method sets
     */
    public void updateCertifLaboral(Long userId, byte[] certifLaboral) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setCertifLaboral(certifLaboral);
            userRepository.save(user);
        } else {
            throw new RuntimeException("Usuario no encontrado");
        }
    }

    /**
     * This Java function updates the academic certificate of a user identified by
     * their ID in the
     * database.
     * 
     * @param userId          The `userId` parameter is the unique identifier of the
     *                        user whose academic
     *                        certificate needs to be updated.
     * @param certifAcademica The `certifAcademica` parameter in the
     *                        `updateCertifAcademica` method
     *                        is a byte array that represents the academic
     *                        certificate of a user. This method updates the
     *                        academic certificate of a user identified by their
     *                        `userId` in the database.
     */
    public void updateCertifAcademica(Long userId, byte[] certifAcademica) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setCertifAcademica(certifAcademica);
            userRepository.save(user);
        } else {
            throw new RuntimeException("Usuario no encontrado");
        }
    }

    /**
     * The function updates the certificate of a user identified by their ID in a
     * Java application.
     * 
     * @param userId    The `userId` parameter is the unique identifier of the user
     *                  whose `certifEps`
     *                  needs to be updated.
     * @param certifEps The `certifEps` parameter in the `updateCertifEps` method is
     *                  a byte array
     *                  that represents the certificate of the user's EPS (Entidad
     *                  Promotora de Salud). This method
     *                  updates the EPS certificate for a specific user identified
     *                  by their `userId`.
     */
    public void updateCertifEps(Long userId, byte[] certifEps) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setCertifEps(certifEps);
            userRepository.save(user);
        } else {
            throw new RuntimeException("Usuario no encontrado");
        }
    }

    /**
     * This Java function updates the certificate of a user's pension fund based on
     * the user's ID.
     * 
     * @param userId             The `userId` parameter is the unique identifier of
     *                           the user whose fondo de
     *                           pensiones (pension fund certificate) needs to be
     *                           updated in the system.
     * @param certifFondoPension The `certifFondoPension` parameter in the
     *                           `updateCertifFondoPension` method is of type
     *                           `byte[]`, which means it is an array of bytes.
     *                           This parameter is used to update the certificate of
     *                           the pension fund for a specific user
     *                           identified by their `userId`.
     */
    public void updateCertifFondoPension(Long userId, byte[] certifFondoPension) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setCertifFondoPension(certifFondoPension);
            userRepository.save(user);
        } else {
            throw new RuntimeException("Usuario no encontrado");
        }
    }

    /**
     * The function updates the certification ARL of a user identified by their ID
     * in a Java
     * application.
     * 
     * @param userId    The `userId` parameter is the unique identifier of the user
     *                  whose `certifArl`
     *                  needs to be updated.
     * @param certifArl The `certifArl` parameter in the `updateCertifArl` method is
     *                  a byte array
     *                  that represents a certificate related to the user identified
     *                  by the `userId`. This method
     *                  updates the `certifArl` field of the user entity in the
     *                  database with the provided byte
     *                  array.
     */
    public void updateCertifArl(Long userId, byte[] certifArl) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setCertifArl(certifArl);
            userRepository.save(user);
        } else {
            throw new RuntimeException("Usuario no encontrado");
        }
    }

    /**
     * The function updates the bank certificate of a user identified by their ID in
     * a Java
     * application.
     * 
     * @param userId         The `userId` parameter is the unique identifier of the
     *                       user whose banking
     *                       certificate needs to be updated.
     * @param certifBancario The `certifBancario` parameter in the
     *                       `updateCertifBancario` method is
     *                       a byte array that represents the user's banking
     *                       certificate. This method updates the banking
     *                       certificate of a user identified by their `userId`. If
     *                       the user is found in the repository,
     *                       the method sets the
     */
    public void updateCertifBancario(Long userId, byte[] certifBancario) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setCertifBancario(certifBancario);
            userRepository.save(user);
        } else {
            throw new RuntimeException("Usuario no encontrado");
        }
    }

    /**
     * This Java function updates the disciplinary background certificate for a user
     * identified by
     * their ID.
     * 
     * @param userId                     The `userId` parameter is the unique
     *                                   identifier of the user for whom the
     *                                   disciplinary background certificate is
     *                                   being updated.
     * @param certifAntecedDisciplinario The parameter `certifAntecedDisciplinario`
     *                                   is a byte array
     *                                   that represents the updated certificate of
     *                                   disciplinary background for a user
     *                                   identified by
     *                                   their `userId`. This method updates the
     *                                   certificate for the specified user in the
     *                                   database.
     */
    public void updateCertifAntecedDisciplinario(Long userId, byte[] certifAntecedDisciplinario) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setCertifAntecedDisciplinario(certifAntecedDisciplinario);
            userRepository.save(user);
        } else {
            throw new RuntimeException("Usuario no encontrado");
        }
    }

    /**
     * The function `updateCertifAntecedFiscales` updates the certificate of
     * criminal records for a
     * user identified by their ID.
     * 
     * @param userId                The `userId` parameter is the unique identifier
     *                              of the user for whom the
     *                              certificate of criminal record
     *                              (certifAntecedFiscales) is being updated.
     * @param certifAntecedFiscales The `updateCertifAntecedFiscales` method takes
     *                              in a `userId`
     *                              and a byte array `certifAntecedFiscales` as
     *                              parameters. The `certifAntecedFiscales`
     *                              parameter represents the updated certificate of
     *                              criminal record for the user identified by
     *                              the `userId
     */
    public void updateCertifAntecedFiscales(Long userId, byte[] certifAntecedFiscales) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setCertifAntecedFiscales(certifAntecedFiscales);
            userRepository.save(user);
        } else {
            throw new RuntimeException("Usuario no encontrado");
        }
    }

    public void updateCertifAntecedPenales(Long userId, byte[] certifAntecedPenales) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setCertifAntecedPenales(certifAntecedPenales);
            userRepository.save(user);
        } else {
            throw new RuntimeException("Usuario no encontrado");
        }
    }

    public void updateLibretaMilitar(Long userId, byte[] libretaMilitar) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setLibretaMilitar(libretaMilitar);
            userRepository.save(user);
        } else {
            throw new RuntimeException("Usuario no encontrado");
        }
    }

    public void updateCertifMedOcupacional(Long userId, byte[] certifMedOcupacional) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setCertifMedOcupacional(certifMedOcupacional);
            userRepository.save(user);
        } else {
            throw new RuntimeException("Usuario no encontrado");
        }
    }

    public void updateReferPersonales(Long userId, byte[] referPersonales) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setReferPersonales(referPersonales);
            userRepository.save(user);
        } else {
            throw new RuntimeException("Usuario no encontrado");
        }
    }
}