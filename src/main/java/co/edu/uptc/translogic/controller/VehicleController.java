package co.edu.uptc.translogic.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import co.edu.uptc.translogic.domain.CargoVehicle;
import co.edu.uptc.translogic.domain.PassengerVehicle;
import co.edu.uptc.translogic.domain.Vehicle;
import co.edu.uptc.translogic.service.VehicleService;

@Controller
@RequestMapping("/vehiculos")
public class VehicleController {
    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping
    public String listVehicles(Model model) {
        model.addAttribute("vehiculos", vehicleService.getAllVehicles());
        return "Vehiculos";
    }

    @GetMapping("/crear")
    public String showCreateForm(Model model) {
        model.addAttribute("vehiculo", new PassengerVehicle());
        return "forms/FormVehiculo"; // Ajustado a la ruta correcta
    }

    @PostMapping("/crear")
    public String createVehicle(
            @RequestParam String type,
            @RequestParam String plate,
            @RequestParam String model,
            @RequestParam Integer year,
            @RequestParam(required = false) Double loadCapacity,
            @RequestParam(required = false) Integer passengerCapacity) {

        Vehicle newVehicle;
        if ("CARGO".equalsIgnoreCase(type)) {
            CargoVehicle cargoVehicle = new CargoVehicle();
            cargoVehicle.setLoadCapacity(Optional.ofNullable(loadCapacity).orElse(0.0));
            newVehicle = cargoVehicle;
        } else {
            PassengerVehicle passengerVehicle = new PassengerVehicle();
            passengerVehicle.setPassengerCapacity(Optional.ofNullable(passengerCapacity).orElse(0));
            newVehicle = passengerVehicle;
        }
        newVehicle.setPlate(plate);
        newVehicle.setModel(model);
        newVehicle.setYear(year);
        newVehicle.setType(type);

        vehicleService.saveVehicle(newVehicle);
        return "redirect:/vehiculos";
    }

    @GetMapping("/editar/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Vehicle vehicle = vehicleService.getVehicleById(id);
        if (vehicle != null) {
            if (vehicle instanceof CargoVehicle) {
                vehicle.setType("CARGO");
            } else if (vehicle instanceof PassengerVehicle) {
                vehicle.setType("PASSENGER");
            }
            model.addAttribute("vehiculo", vehicle);
            return "forms/FormVehiculo"; // Ajustado a la ruta correcta
        }
        return "redirect:/vehiculos";
    }

    @PostMapping("/editar/{id}")
    public String updateVehicle(
            @PathVariable Long id,
            @RequestParam String type,
            @RequestParam String plate,
            @RequestParam String model,
            @RequestParam Integer year,
            @RequestParam(required = false) Double loadCapacity,
            @RequestParam(required = false) Integer passengerCapacity) {

        Vehicle existingVehicle = vehicleService.getVehicleById(id);
        if (existingVehicle != null) {
            existingVehicle.setPlate(plate);
            existingVehicle.setModel(model);
            existingVehicle.setYear(year);
            existingVehicle.setType(type);

            if ("CARGO".equalsIgnoreCase(type) && existingVehicle instanceof CargoVehicle) {
                ((CargoVehicle) existingVehicle).setLoadCapacity(Optional.ofNullable(loadCapacity).orElse(0.0));
            } else if ("PASSENGER".equalsIgnoreCase(type) && existingVehicle instanceof PassengerVehicle) {
                ((PassengerVehicle) existingVehicle).setPassengerCapacity(Optional.ofNullable(passengerCapacity).orElse(0));
            }
            vehicleService.saveVehicle(existingVehicle);
        }
        return "redirect:/vehiculos";
    }

    @GetMapping("/eliminar/{id}")
    public String deleteVehicle(@PathVariable Long id) {
        vehicleService.deleteVehicle(id);
        return "redirect:/vehiculos";
    }
}
