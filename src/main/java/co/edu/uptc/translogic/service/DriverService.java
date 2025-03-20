package co.edu.uptc.translogic.service;

import java.util.List;

import org.springframework.stereotype.Service;

import co.edu.uptc.translogic.domain.Driver;
import co.edu.uptc.translogic.repository.DriverRepository;

@Service
public class DriverService {
    private final DriverRepository driverRepository;

    public DriverService(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    public List<Driver> getAllDrivers() {
        return driverRepository.findAll();
    }

    public Driver getDriverById(Long id) {
        return driverRepository.findById(id).orElse(null);
    }

    public void createDriver(Driver driver) {
        driverRepository.save(driver);
    }

    public void updateDriver(Long id, Driver driverDetails) {
        Driver existingDriver = driverRepository.findById(id).orElse(null);
        if (existingDriver != null) {
            existingDriver.setLicenseNumber(driverDetails.getLicenseNumber());
            existingDriver.setAvailable(driverDetails.getAvailable());
            existingDriver.setExperience(driverDetails.getExperience());
            existingDriver.setUser(driverDetails.getUser());
            driverRepository.save(existingDriver);
        }
    }

    public void deleteDriver(Long id) {
        driverRepository.deleteById(id);
    }
}