package com.example.demo.Services;

import com.example.demo.Dto.RegisterDto;
import com.example.demo.Model.Entity.User;
import com.example.demo.Model.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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

    public String updateUserPdf(Long id, String documentType, MultipartFile file) {
        try {
            Optional<User> optionalUser = userRepository.findById(id);
            if (optionalUser.isEmpty()) {
                return "Usuario no encontrado";
            }

            User user = optionalUser.get();
            byte[] fileBytes = file.getBytes();

            switch (documentType) {
                case "cedulaPdf":
                    user.setCedulaPdf(fileBytes);
                    break;
                case "cv":
                    user.setCv(fileBytes);
                    break;
                case "certifLaboral":
                    user.setCertifLaboral(fileBytes);
                    break;
                case "certifAcademica":
                    user.setCertifAcademica(fileBytes);
                    break;
                case "certifEps":
                    user.setCertifEps(fileBytes);
                    break;
                case "certifFondoPension":
                    user.setCertifFondoPension(fileBytes);
                    break;
                case "certifArl":
                    user.setCertifArl(fileBytes);
                    break;
                case "certifBancario":
                    user.setCertifBancario(fileBytes);
                    break;
                case "certifAntecedDisciplinario":
                    user.setCertifAntecedDisciplinario(fileBytes);
                    break;
                case "certifAntecedFiscales":
                    user.setCertifAntecedFiscales(fileBytes);
                    break;
                case "certifAntecedPenales":
                    user.setCertifAntecedPenales(fileBytes);
                    break;
                case "libretaMilitar":
                    user.setLibretaMilitar(fileBytes);
                    break;
                case "certifMedOcupacional":
                    user.setCertifMedOcupacional(fileBytes);
                    break;
                case "referPersonales":
                    user.setReferPersonales(fileBytes);
                    break;
                default:
                    return "Tipo de documento no válido";
            }

            userRepository.save(user);
            return "Documento PDF actualizado correctamente";
        } catch (Exception e) {
            return "Error al actualizar el documento PDF: " + e.getMessage();
        }
    }

    public byte[] getUserPdf(Long id, String documentType) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            return null;
        }

        User user = optionalUser.get();
        byte[] fileData = null;

        switch (documentType) {
            case "cedulaPdf":
                fileData = user.getCedulaPdf();
                break;
            case "cv":
                fileData = user.getCv();
                break;
            case "certifLaboral":
                fileData = user.getCertifLaboral();
                break;
            case "certifAcademica":
                fileData = user.getCertifAcademica();
                break;
            case "certifEps":
                fileData = user.getCertifEps();
                break;
            case "certifFondoPension":
                fileData = user.getCertifFondoPension();
                break;
            case "certifArl":
                fileData = user.getCertifArl();
                break;
            case "certifBancario":
                fileData = user.getCertifBancario();
                break;
            case "certifAntecedDisciplinario":
                fileData = user.getCertifAntecedDisciplinario();
                break;
            case "certifAntecedFiscales":
                fileData = user.getCertifAntecedFiscales();
                break;
            case "certifAntecedPenales":
                fileData = user.getCertifAntecedPenales();
                break;
            case "libretaMilitar":
                fileData = user.getLibretaMilitar();
                break;
            case "certifMedOcupacional":
                fileData = user.getCertifMedOcupacional();
                break;
            case "referPersonales":
                fileData = user.getReferPersonales();
                break;
            default:
                return null;
        }

        return fileData;
    }
}