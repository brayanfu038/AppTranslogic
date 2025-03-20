package co.edu.uptc.translogic.domain;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Driver")
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "license_number", unique = true, nullable = false, length = 255)
    private String licenseNumber;
    
    @Column(name = "is_available", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean available;
    
    @Column(columnDefinition = "INT DEFAULT 0")
    private Integer experience;
    
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @OneToMany(mappedBy = "driver")
    private Set<Trip> trips = new HashSet<>();
    
    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Trip> getTrips() {
        return trips;
    }

    public void setTrips(Set<Trip> trips) {
        this.trips = trips;
    }

    // Métodos adicionales para Thymeleaf
    public String getName() {
        return user != null ? user.getName() : "Sin nombre";
    }

    public String getLastName() {
        return user != null ? user.getLastName() : "Sin apellido";
    }
}
