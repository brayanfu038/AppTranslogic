package co.edu.uptc.translogic.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "trip")
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 255)
    private String origin;
    
    @Column(nullable = false, length = 255)
    private String destination;
    
    @Column(name = "departure_time", nullable = false)
    private LocalDateTime departureTime;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TripStatus status;
    
    @ManyToOne
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;
    
    @ManyToOne
    @JoinColumn(name = "driver_id", nullable = false)
    private Driver driver;

    // Constructor vacío necesario para JPA
    public Trip() {
        this.departureTime = LocalDateTime.now(); // Evita valores nulos
    }

    // Constructor con parámetros
    public Trip(String origin, String destination, LocalDateTime departureTime, TripStatus status, Vehicle vehicle, Driver driver) {
        this.origin = origin;
        this.destination = destination;
        this.departureTime = departureTime != null ? departureTime : LocalDateTime.now();
        this.status = status;
        this.vehicle = vehicle;
        this.driver = driver;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getOrigin() { return origin; }
    public void setOrigin(String origin) { this.origin = origin; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public LocalDateTime getDepartureTime() { return departureTime; }
    public void setDepartureTime(LocalDateTime departureTime) { this.departureTime = departureTime; }

    public TripStatus getStatus() { return status; }
    public void setStatus(TripStatus status) { this.status = status; }

    public Vehicle getVehicle() { return vehicle; }
    public void setVehicle(Vehicle vehicle) { this.vehicle = vehicle; }

    public Driver getDriver() { return driver; }
    public void setDriver(Driver driver) { this.driver = driver; }

    public enum TripStatus {
        SCHEDULED, 
        IN_PROGRESS, 
        COMPLETED, 
        CANCELLED
    }
}
