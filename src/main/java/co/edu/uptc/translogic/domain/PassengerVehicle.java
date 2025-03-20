package co.edu.uptc.translogic.domain;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("PASSENGER")
public class PassengerVehicle extends Vehicle {
    public PassengerVehicle() {
        setType("PASSENGER"); // Establecer el tipo al crear la instancia
    }
    @Column(name = "passenger_capacity")
    private Integer passengerCapacity;

    public Integer getPassengerCapacity() {
        return passengerCapacity;
    }

    public void setPassengerCapacity(Integer passengerCapacity) {
        this.passengerCapacity = passengerCapacity;
    }
}