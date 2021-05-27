package edu.bstu.xyloteka.xyloteka.repo;

import edu.bstu.xyloteka.xyloteka.models.Names;
import edu.bstu.xyloteka.xyloteka.models.Sample;
import edu.bstu.xyloteka.xyloteka.models.SampleProperty;
import edu.bstu.xyloteka.xyloteka.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface SampleRepository extends JpaRepository<Sample, Long> {
    Optional<Sample> findById(long id);

    Page<Sample> findByTrade(boolean trade, Pageable pageable);

    Page<Sample> findByNames(Names names, Pageable pageable);

    Page<Sample> findByNamesTradeNameContaining(String tradeName, Pageable pageable);

    Page<Sample> findByNamesAltNameContaining(String alt, Pageable pageable);

    Page<Sample> findByPlaceContaining(String place, Pageable pageable);

    Page<Sample> findByWhoCollect(User user, Pageable pageable);

    Page<Sample> findByWhoCollectId(long id, Pageable pageable);

    Page<Sample> findByWhoDefine(User user, Pageable pageable);

    Page<Sample> findByWhoDefineId(long id, Pageable pageable);

    Page<Sample> findByCollectDate(Date date, Pageable pageable);

    Page<Sample> findByProperty(SampleProperty property, Pageable pageable);

    Page<Sample> findByPropertyDensityContaining(String density, Pageable pageable);

    Page<Sample> findByPropertyHardnessContaining(String hardness, Pageable pageable);

    Page<Sample> findByPropertyShrinkageContaining(String shrinkage, Pageable pageable);

    Page<Sample> findByApprove(boolean approve, Pageable pageable);
}
