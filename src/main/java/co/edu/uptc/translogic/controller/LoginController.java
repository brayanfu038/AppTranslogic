package co.edu.uptc.translogic.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import co.edu.uptc.translogic.domain.User;
import co.edu.uptc.translogic.repository.UserRepository;

@Controller
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String homeRedirect() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginForm(@RequestParam(value = "error", required = false) String error, 
                            @RequestParam(value = "logout", required = false) String logout,
                            Model model) {
        if (error != null) {
            model.addAttribute("error", "Credenciales incorrectas.");
        }
        if (logout != null) {
            model.addAttribute("message", "Has cerrado sesión exitosamente.");
        }
        logger.info("Accediendo a la página de login");
        return "index/Login";
    }

    @PostMapping("/login")
    public String loginSubmit(@RequestParam String username, 
                              @RequestParam String password, Model model) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
    
        if (optionalUser.isEmpty() || !optionalUser.get().getPasswordHash().equals(password)) {
            model.addAttribute("error", "Credenciales incorrectas.");
            return "index/Login";
        }
    
        User user = optionalUser.get();
        switch (user.getRol()) {
            case ADMIN -> {
                return "redirect:/indexAdmin";
            }
            case DISPATCHER -> {
                return "redirect:/indexDispatcher";
            }
            case DRIVER -> {
                return "redirect:/indexDriver";
            }
            default -> {
                model.addAttribute("error", "Rol desconocido.");
                return "index/Login";
            }
        }
    }
    
    @GetMapping("/indexAdmin")
    public String indexAdmin() {
        return "index/IndexAdmin"; 
    }

    @GetMapping("/indexDispatcher")
    public String indexDispatcher() {
        return "index/IndexDispatcher"; 
    }

    @GetMapping("/indexDriver")
    public String indexDriver() {
        return "index/IndexDriver"; 
    }
}
