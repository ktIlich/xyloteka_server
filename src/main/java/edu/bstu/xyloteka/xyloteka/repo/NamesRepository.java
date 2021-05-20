package edu.bstu.xyloteka.xyloteka.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import edu.bstu.xyloteka.xyloteka.models.Names;

@RepositoryRestResource(collectionResourceRel = "Names", path = "Names")
public interface NamesRepository extends CrudRepository<Names, Long> {

}
