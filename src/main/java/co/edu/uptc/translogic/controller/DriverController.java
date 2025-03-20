package co.edu.uptc.translogic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import co.edu.uptc.translogic.domain.Driver;
import co.edu.uptc.translogic.service.DriverService;
import co.edu.uptc.translogic.service.UserService;

@Controller
@RequestMapping("/conductores")
public class DriverController {
    private final DriverService driverService;
    private final UserService userService;

    public DriverController(DriverService driverService, UserService userService) {
        this.driverService = driverService;
        this.userService = userService;
    }

    @GetMapping
    public String listDrivers(Model model) {
        model.addAttribute("conductores", driverService.getAllDrivers());
        return "Conductores";
    }

    @GetMapping("/crear")
    public String showCreateForm(Model model) {
        Driver driver = new Driver();
        model.addAttribute("conductor", driver);
        model.addAttribute("usuarios", userService.getAllUsers()); // Lista de usuarios para asociar al conductor
        return "FormConductor";
    }

    @PostMapping("/crear")
    public String createDriver(@ModelAttribute Driver driver) {
        driverService.createDriver(driver);
        return "redirect:/conductores";
    }

    @GetMapping("/editar/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Driver driver = driverService.getDriverById(id);
        if (driver != null) {
            model.addAttribute("conductor", driver);
            model.addAttribute("usuarios", userService.getAllUsers()); // Lista de usuarios para asociar al conductor
            return "FormConductor";
        }
        return "redirect:/conductores";
    }

    @PostMapping("/editar/{id}")
    public String updateDriver(@PathVariable Long id, @ModelAttribute Driver driver) {
        driverService.updateDriver(id, driver);
        return "redirect:/conductores";
    }

    @GetMapping("/eliminar/{id}")
    public String deleteDriver(@PathVariable Long id) {
        driverService.deleteDriver(id);
        return "redirect:/conductores";
    }
}