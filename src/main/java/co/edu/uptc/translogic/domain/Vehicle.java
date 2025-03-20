package co.edu.uptc.translogic.domain;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "Vehicle")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public abstract class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false, length = 20)
    private String plate;
    
    @Column(nullable = false, length = 100)
    private String model;
    
    @Column(nullable = false)
    private Integer year;
    
    @Column(name = "load_capacity", nullable = true)
    private Double loadCapacity; // Solo para CARGO
    
    @Column(name = "passenger_capacity", nullable = true)
    private Integer passengerCapacity; // Solo para PASSENGER
    
    @ManyToMany(mappedBy = "vehicles")
    private Set<Flota> flotas = new HashSet<>();

    @Transient // No se persiste, solo para acceso en la vista
    private String type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
    
    public Double getLoadCapacity() {
        return loadCapacity;
    }

    public void setLoadCapacity(Double loadCapacity) {
        this.loadCapacity = loadCapacity;
    }

    public Integer getPassengerCapacity() {
        return passengerCapacity;
    }

    public void setPassengerCapacity(Integer passengerCapacity) {
        this.passengerCapacity = passengerCapacity;
    }

    public Set<Flota> getFlotas() {
        return flotas;
    }

    public void setFlotas(Set<Flota> flotas) {
        this.flotas = flotas;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
