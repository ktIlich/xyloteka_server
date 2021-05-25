package edu.bstu.xyloteka.xyloteka.repo;

import edu.bstu.xyloteka.xyloteka.models.Ordo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrdoRepository extends JpaRepository<Ordo, Long> {
    Optional<Ordo> findById(long id);
    Optional<Ordo> findByName(String name);
    List<Ordo> findByNameContaining(String name);
}
