package edu.bstu.xyloteka.xyloteka.repo;

import edu.bstu.xyloteka.xyloteka.models.Division;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DivisionRepository extends JpaRepository<Division, Long> {
    Optional<Division> findById(long id);
    List<Division> findByNameContaining(String name);
}
