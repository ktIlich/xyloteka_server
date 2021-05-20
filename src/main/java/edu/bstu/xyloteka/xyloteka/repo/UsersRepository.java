package edu.bstu.xyloteka.xyloteka.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import edu.bstu.xyloteka.xyloteka.models.User;

@RepositoryRestResource(collectionResourceRel = "User", path = "User")
public interface UsersRepository extends CrudRepository<User, Long> {

}
