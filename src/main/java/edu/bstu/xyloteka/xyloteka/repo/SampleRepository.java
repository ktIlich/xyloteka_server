package edu.bstu.xyloteka.xyloteka.repo;

import java.util.Date;
import java.util.List;

import edu.bstu.xyloteka.xyloteka.models.Names;
import edu.bstu.xyloteka.xyloteka.models.Sample;
import edu.bstu.xyloteka.xyloteka.models.SampleProperty;
import edu.bstu.xyloteka.xyloteka.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SampleRepository extends JpaRepository<Sample, Long> {
    Optional<Sample> findById(long id);
    List<Sample> findByTrade(boolean trade);
    List<Sample> findByNames(Names names);
    List<Sample> findByNamesTradeNameContaining(String tradeName);
    List<Sample> findByNamesAltNameContaining(String alt);
    List<Sample> findByPlaceContaining(String place);
    List<Sample> findByWhoCollect(User user);
    List<Sample> findByWhoCollectId(long id);
    List<Sample> findByWhoDefine(User user);
    List<Sample> findByWhoDefineId(long id);
    List<Sample> findByCollectDate(Date date);
    List<Sample> findByProperty(SampleProperty property);
    List<Sample> findByPropertyDensityContaining(String density);
    List<Sample> findByPropertyHardnessContaining(String hardness);
    List<Sample> findByPropertyShrinkageContaining(String shrinkage);
}
