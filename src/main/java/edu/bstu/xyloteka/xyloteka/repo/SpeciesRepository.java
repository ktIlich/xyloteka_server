package edu.bstu.xyloteka.xyloteka.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import edu.bstu.xyloteka.xyloteka.models.Species;

@RepositoryRestResource(collectionResourceRel = "Species", path = "Species")
public interface SpeciesRepository extends CrudRepository<Species, Long> {

}
