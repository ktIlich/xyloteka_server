package edu.bstu.xyloteka.xyloteka.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import edu.bstu.xyloteka.xyloteka.models.Photo;

@RepositoryRestResource(collectionResourceRel = "Photo", path = "Photo")
public interface SamplePhotosRepository extends CrudRepository<Photo, Long> {

}
