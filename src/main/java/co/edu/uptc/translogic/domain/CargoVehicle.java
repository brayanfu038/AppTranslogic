package co.edu.uptc.translogic.domain;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("CARGO")
public class CargoVehicle extends Vehicle {
    public CargoVehicle() {
        setType("CARGO"); // Establecer el tipo al crear la instancia
    }
    
    @Column(name = "load_capacity", nullable = false)
    private Double loadCapacity;

    public Double getLoadCapacity() {
        return loadCapacity;
    }

    public void setLoadCapacity(Double loadCapacity) {
        this.loadCapacity = loadCapacity;
    }
}
