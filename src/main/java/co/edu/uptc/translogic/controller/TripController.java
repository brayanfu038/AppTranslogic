package co.edu.uptc.translogic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import co.edu.uptc.translogic.domain.Trip;
import co.edu.uptc.translogic.domain.Trip.TripStatus;
import co.edu.uptc.translogic.service.DriverService;
import co.edu.uptc.translogic.service.TripService;
import co.edu.uptc.translogic.service.VehicleService;

@Controller
@RequestMapping("/viajes")
public class TripController {
    private final TripService tripService;
    private final VehicleService vehicleService;
    private final DriverService driverService;

    public TripController(TripService tripService, VehicleService vehicleService, DriverService driverService) {
        this.tripService = tripService;
        this.vehicleService = vehicleService;
        this.driverService = driverService;
    }

    // Listar todos los viajes
    @GetMapping
    public String listTrips(Model model) {
        model.addAttribute("viajes", tripService.getAllTrips());
        return "Viajes";
    }

    // Mostrar formulario para crear un nuevo viaje
    @GetMapping("/crear")
    public String showCreateForm(Model model) {
        model.addAttribute("viaje", new Trip());
        model.addAttribute("vehiculos", vehicleService.getAllVehicles()); // Lista de vehículos
        model.addAttribute("conductores", driverService.getAllDrivers()); // Lista de conductores
        model.addAttribute("estados", TripStatus.values()); // Lista de estados del viaje
        return "forms/FormViaje"; // Ajustado a la ruta correcta
    }

    // Procesar la creación de un nuevo viaje
    @PostMapping("/crear")
    public String createTrip(@ModelAttribute Trip trip) {
        tripService.createTrip(trip);
        return "redirect:/viajes";
    }

    // Mostrar formulario para editar un viaje existente
    @GetMapping("/editar/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Trip trip = tripService.getTripById(id);
        if (trip != null) {
            model.addAttribute("viaje", trip);
            model.addAttribute("vehiculos", vehicleService.getAllVehicles());
            model.addAttribute("conductores", driverService.getAllDrivers());
            model.addAttribute("estados", TripStatus.values());
            return "forms/FormViaje"; // Ajustado a la ruta correcta
        }
        return "redirect:/viajes";
    }

    // Procesar la actualización de un viaje existente
    @PostMapping("/editar/{id}")
    public String updateTrip(@PathVariable Long id, @ModelAttribute Trip trip) {
        tripService.updateTrip(id, trip);
        return "redirect:/viajes";
    }

    // Eliminar un viaje
    @GetMapping("/eliminar/{id}")
    public String deleteTrip(@PathVariable Long id) {
        tripService.deleteTrip(id);
        return "redirect:/viajes";
    }
}
