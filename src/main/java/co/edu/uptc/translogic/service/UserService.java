package co.edu.uptc.translogic.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uptc.translogic.domain.User;
import co.edu.uptc.translogic.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    // Inyección de dependencias por constructor
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Obtener todos los usuarios
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Buscar usuario por ID
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // Buscar usuario por nombre de usuario
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // Verificar si un usuario ya existe por nombre de usuario
    public boolean userExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    // Crear usuario sin codificación de contraseña
    @Transactional
    public User createUser(User user) {
        if (userExists(user.getUsername())) {
            throw new IllegalArgumentException("El nombre de usuario ya está en uso.");
        }
        return userRepository.save(user);
    }

    // Actualizar usuario
    @Transactional
    public User updateUser(Long id, User userDetails) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    // Actualizar campos si están presentes en userDetails
                    if (userDetails.getUsername() != null) {
                        existingUser.setUsername(userDetails.getUsername());
                    }
                    if (userDetails.getRol() != null) {
                        existingUser.setRol(userDetails.getRol());
                    }
                    if (userDetails.getPasswordHash() != null && !userDetails.getPasswordHash().isEmpty()) {
                        existingUser.setPasswordHash(userDetails.getPasswordHash());
                    }
                    return userRepository.save(existingUser);
                })
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con ID: " + id));
    }

    // Eliminar usuario
    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("Usuario no encontrado con ID: " + id);
        }
        userRepository.deleteById(id);
    }
}