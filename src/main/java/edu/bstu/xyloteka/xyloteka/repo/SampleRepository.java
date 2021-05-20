package edu.bstu.xyloteka.xyloteka.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import edu.bstu.xyloteka.xyloteka.models.Sample;

@RepositoryRestResource(collectionResourceRel = "Sample", path = "Sample")
public interface SampleRepository extends CrudRepository<Sample, Long> {

}
