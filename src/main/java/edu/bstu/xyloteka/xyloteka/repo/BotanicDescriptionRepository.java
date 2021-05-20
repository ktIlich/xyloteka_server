package edu.bstu.xyloteka.xyloteka.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import edu.bstu.xyloteka.xyloteka.models.BotanicDescription;

@RepositoryRestResource(collectionResourceRel = "BotanicDescription", path = "BotanicDescription")
public interface BotanicDescriptionRepository extends CrudRepository<BotanicDescription, Long> {

}
