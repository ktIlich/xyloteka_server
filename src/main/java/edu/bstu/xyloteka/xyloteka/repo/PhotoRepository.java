package edu.bstu.xyloteka.xyloteka.repo;

import edu.bstu.xyloteka.xyloteka.models.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {
    List<Photo> findBySample_id(long id);
    Photo findBySample_idAndPhoto(long id, String name);
}
