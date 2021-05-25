package edu.bstu.xyloteka.xyloteka.repo;
import java.util.List;
import edu.bstu.xyloteka.xyloteka.models.Classis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClassisRepository extends JpaRepository<Classis, Long> {
    Optional<Classis> findById(long id);
    Optional<Classis> findByName(String name);
    List<Classis> findByNameContaining(String name);
}
