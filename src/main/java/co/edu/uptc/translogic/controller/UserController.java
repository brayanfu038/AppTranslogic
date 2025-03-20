package co.edu.uptc.translogic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import co.edu.uptc.translogic.domain.User;
import co.edu.uptc.translogic.domain.User.RoleName;
import co.edu.uptc.translogic.service.UserService;

@Controller
@RequestMapping("/usuarios")
public class UserController {
    private final UserService userService;

    // Inyección de dependencias por constructor
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Listar usuarios
    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("usuarios", userService.getAllUsers());
        return "Usuarios";
    }

    // Mostrar formulario de creación
    @GetMapping("/crear")
    public String showCreateForm(Model model) {
        model.addAttribute("usuario", new User());
        model.addAttribute("roles", RoleName.values()); // Pasar roles disponibles a la vista
        return "forms/FormUsuario";
    }

    // Procesar creación de usuario
    @PostMapping("/crear")
    public String createUser(@ModelAttribute User user, @RequestParam RoleName rol, Model model) {
        if (userService.userExists(user.getUsername())) {
            model.addAttribute("error", "El nombre de usuario ya está en uso.");
            model.addAttribute("usuario", user);
            model.addAttribute("roles", RoleName.values());
            return "forms/FormUsuario";
        }
        user.setRol(rol);
        userService.createUser(user);
        return "redirect:/usuarios";
    }

    // Mostrar formulario de edición
    @GetMapping("/editar/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        return userService.getUserById(id)
                .map(user -> {
                    model.addAttribute("usuario", user);
                    model.addAttribute("roles", RoleName.values());
                    return "forms/FormUsuario";
                })
                .orElse("redirect:/usuarios"); // Redirigir si el usuario no existe
    }

    // Procesar edición de usuario
    @PostMapping("/editar/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute User user, @RequestParam RoleName rol, Model model) {
        return userService.getUserById(id)
                .map(existingUser -> {
                    // Actualizar campos si están presentes
                    if (user.getUsername() != null) {
                        existingUser.setUsername(user.getUsername());
                    }
                    if (user.getPasswordHash() != null && !user.getPasswordHash().isEmpty()) {
                        existingUser.setPasswordHash(user.getPasswordHash());
                    }
                    existingUser.setRol(rol);
                    userService.updateUser(id, existingUser);
                    return "redirect:/usuarios";
                })
                .orElse("redirect:/usuarios"); // Redirigir si el usuario no existe
    }

    // Eliminar usuario
    @GetMapping("/eliminar/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/usuarios";
    }
}