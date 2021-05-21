package edu.bstu.xyloteka.xyloteka.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import edu.bstu.xyloteka.xyloteka.models.Ordo;

@RepositoryRestResource(collectionResourceRel = "Order", path = "Order")
public interface OrderRepository extends CrudRepository<Ordo, Long> {

}
