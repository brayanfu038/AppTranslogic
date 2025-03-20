package co.edu.uptc.translogic.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uptc.translogic.domain.Flota;
import co.edu.uptc.translogic.repository.FlotaRepository;

@Service
public class FlotaService {

    private final FlotaRepository flotaRepository;

    // Inyección de dependencias por constructor
    public FlotaService(FlotaRepository flotaRepository) {
        this.flotaRepository = flotaRepository;
    }

    // Obtener todas las flotas
    public List<Flota> getAllFlotas() {
        return flotaRepository.findAll();
    }

    // Buscar flota por ID
    public Optional<Flota> getFlotaById(Long id) {
        return flotaRepository.findById(id);
    }

    // Crear flota
    @Transactional
    public Flota createFlota(Flota flota) {
        return flotaRepository.save(flota);
    }

    // Actualizar flota
    @Transactional
    public Flota updateFlota(Long id, Flota flotaDetails) {
        return flotaRepository.findById(id)
                .map(existingFlota -> {
                    // Actualizar campos si están presentes en flotaDetails
                    if (flotaDetails.getName() != null) {
                        existingFlota.setName(flotaDetails.getName());
                    }
                    if (flotaDetails.getVehicles() != null) {
                        existingFlota.setVehicles(flotaDetails.getVehicles());
                    }
                    return flotaRepository.save(existingFlota);
                })
                .orElseThrow(() -> new IllegalArgumentException("Flota no encontrada con ID: " + id));
    }

    // Eliminar flota
    @Transactional
    public void deleteFlota(Long id) {
        if (!flotaRepository.existsById(id)) {
            throw new IllegalArgumentException("Flota no encontrada con ID: " + id);
        }
        flotaRepository.deleteById(id);
    }
}