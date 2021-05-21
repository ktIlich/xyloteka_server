package edu.bstu.xyloteka.xyloteka.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import edu.bstu.xyloteka.xyloteka.models.Status;

@RepositoryRestResource(collectionResourceRel = "Status", path = "Status")
public interface StatusRepository extends CrudRepository<Status, Long> {

}
