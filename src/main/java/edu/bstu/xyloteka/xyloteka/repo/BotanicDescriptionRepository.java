package edu.bstu.xyloteka.xyloteka.repo;

import edu.bstu.xyloteka.xyloteka.models.BotanicDescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BotanicDescriptionRepository extends JpaRepository<BotanicDescription, Long> {
    Optional<BotanicDescription> findById(long id);
    Optional<BotanicDescription> findByClassisNameContaining(String classisName);
    Optional<BotanicDescription> findByDivisioNameContaining(String divisioName);
    Optional<BotanicDescription> findByGenusNameContaining(String genusName);
    Optional<BotanicDescription> findByFamilyNameContaining(String familyName);
    Optional<BotanicDescription> findByOrdoNameContaining(String ordoName);
    Optional<BotanicDescription> findBySpeciesNameLatContaining(String latName);
    Optional<BotanicDescription> findBySpeciesNameRusContaining(String rusName);
}
