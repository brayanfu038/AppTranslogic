package co.edu.uptc.translogic.service;

import java.util.List;

import org.springframework.stereotype.Service;

import co.edu.uptc.translogic.domain.Trip;
import co.edu.uptc.translogic.repository.TripRepository;

@Service
public class TripService {
    private final TripRepository tripRepository;

    public TripService(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    public List<Trip> getAllTrips() {
        return tripRepository.findAll();
    }

    public Trip getTripById(Long id) {
        return tripRepository.findById(id).orElse(null);
    }

    public void createTrip(Trip trip) {
        tripRepository.save(trip);
    }

    public void updateTrip(Long id, Trip tripDetails) {
        Trip existingTrip = tripRepository.findById(id).orElse(null);
        if (existingTrip != null) {
            existingTrip.setOrigin(tripDetails.getOrigin());
            existingTrip.setDestination(tripDetails.getDestination());
            existingTrip.setDepartureTime(tripDetails.getDepartureTime());
            existingTrip.setStatus(tripDetails.getStatus());
            existingTrip.setVehicle(tripDetails.getVehicle());
            existingTrip.setDriver(tripDetails.getDriver());
            tripRepository.save(existingTrip);
        }
    }

    public void deleteTrip(Long id) {
        tripRepository.deleteById(id);
    }
}