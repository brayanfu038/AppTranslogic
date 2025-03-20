package co.edu.uptc.translogic.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.uptc.translogic.domain.Flota;

@Repository
public interface FlotaRepository extends JpaRepository<Flota, Long> {
    // Puedes agregar métodos personalizados aquí si es necesario
}