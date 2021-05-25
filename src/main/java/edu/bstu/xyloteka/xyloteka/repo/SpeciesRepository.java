package edu.bstu.xyloteka.xyloteka.repo;

import edu.bstu.xyloteka.xyloteka.models.Species;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpeciesRepository extends JpaRepository<Species, Long> {
    Optional<Species> findById(long id);
    Optional<Species> findByNameRusOrNameLat(String nameRus, String nameLat);
    Optional<Species> findByNameRusAndNameLat(String nameRus, String nameLat);

    List<Species> findByNameRusContainingOrNameLatContaining(String rus, String lat);
}
