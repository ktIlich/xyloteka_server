package edu.bstu.xyloteka.xyloteka.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import edu.bstu.xyloteka.xyloteka.models.Classis;

@RepositoryRestResource(collectionResourceRel = "Classis", path = "Classis")
public interface ClassisRepository extends CrudRepository<Classis, Long> {

}
