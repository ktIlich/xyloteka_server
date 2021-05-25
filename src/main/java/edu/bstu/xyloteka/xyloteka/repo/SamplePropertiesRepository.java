package edu.bstu.xyloteka.xyloteka.repo;

import java.util.List;

import edu.bstu.xyloteka.xyloteka.models.Names;
import edu.bstu.xyloteka.xyloteka.models.SampleProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SamplePropertiesRepository extends JpaRepository<SampleProperty, Long> {
    Optional<SampleProperty> findById(long id);
    List<SampleProperty> findByDensityOrHardnessOrShrinkage(String density, String hardness, String shrinkage);
    List<SampleProperty> findByDensityAndHardnessAndShrinkage(String density, String hardness, String shrinkage);
    List<SampleProperty> findByDensity(String density);
    List<SampleProperty> findByHardness(String hardness);
    List<SampleProperty> findByShrinkage(String shrinkage);
}
