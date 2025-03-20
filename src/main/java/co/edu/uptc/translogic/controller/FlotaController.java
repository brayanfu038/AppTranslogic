package co.edu.uptc.translogic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import co.edu.uptc.translogic.domain.Flota;
import co.edu.uptc.translogic.service.FlotaService;

@Controller
@RequestMapping("/flotas")
public class FlotaController {
    private final FlotaService flotaService;

    // Inyección de dependencias por constructor
    public FlotaController(FlotaService flotaService) {
        this.flotaService = flotaService;
    }

    // Listar flotas
    @GetMapping
    public String listFlotas(Model model) {
        model.addAttribute("flotas", flotaService.getAllFlotas());
        return "Flotas";
    }

    // Mostrar formulario de creación
    @GetMapping("/crear")
    public String showCreateForm(Model model) {
        model.addAttribute("flota", new Flota());
        return "forms/FormFlota";
    }

    // Procesar creación de flota
    @PostMapping("/crear")
    public String createFlota(@ModelAttribute Flota flota) {
        flotaService.createFlota(flota);
        return "redirect:/flotas";
    }

    // Mostrar formulario de edición
    @GetMapping("/editar/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        return flotaService.getFlotaById(id)
                .map(flota -> {
                    model.addAttribute("flota", flota);
                    return "forms/FormFlota";
                })
                .orElse("redirect:/flotas"); // Redirigir si la flota no existe
    }

    // Procesar edición de flota
    @PostMapping("/editar/{id}")
    public String updateFlota(@PathVariable Long id, @ModelAttribute Flota flota) {
        flotaService.updateFlota(id, flota);
        return "redirect:/flotas";
    }

    // Eliminar flota
    @GetMapping("/eliminar/{id}")
    public String deleteFlota(@PathVariable Long id) {
        flotaService.deleteFlota(id);
        return "redirect:/flotas";
    }
}