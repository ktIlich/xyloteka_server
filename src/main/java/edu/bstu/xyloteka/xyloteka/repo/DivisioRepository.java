package edu.bstu.xyloteka.xyloteka.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import edu.bstu.xyloteka.xyloteka.models.Division;

@RepositoryRestResource(collectionResourceRel = "divisio", path = "divisio")
public interface DivisioRepository extends CrudRepository<Division, Long> {

}
