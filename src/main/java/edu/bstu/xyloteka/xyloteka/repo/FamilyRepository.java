package edu.bstu.xyloteka.xyloteka.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import edu.bstu.xyloteka.xyloteka.models.Family;

@RepositoryRestResource(collectionResourceRel = "Family", path = "Family")
public interface FamilyRepository extends CrudRepository<Family, Long> {

}
