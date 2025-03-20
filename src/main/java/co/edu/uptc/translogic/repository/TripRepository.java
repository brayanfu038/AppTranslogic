package co.edu.uptc.translogic.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.uptc.translogic.domain.Trip;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
    Optional<Trip> findById(Long id); 
}