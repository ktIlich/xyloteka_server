package edu.bstu.xyloteka.xyloteka.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import edu.bstu.xyloteka.xyloteka.models.SampleProperty;

@RepositoryRestResource(collectionResourceRel = "SampleProperty", path = "SampleProperty")
public interface SamplePropertyRepository extends CrudRepository<SampleProperty, Long> {

}
