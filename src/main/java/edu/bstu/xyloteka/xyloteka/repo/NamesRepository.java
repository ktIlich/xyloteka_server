package edu.bstu.xyloteka.xyloteka.repo;

import edu.bstu.xyloteka.xyloteka.models.Names;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface NamesRepository extends JpaRepository<Names, Long> {
    Optional<Names> findById(long id);
    Optional<Names> findByTradeNameOrAltName(String tradeName, String altName);
    List<Names> findByTradeNameContainingOrAltNameContaining(String tradeName, String altName);
}
