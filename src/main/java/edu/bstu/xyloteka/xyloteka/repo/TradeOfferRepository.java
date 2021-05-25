package edu.bstu.xyloteka.xyloteka.repo;

import java.util.List;
import java.util.Optional;

import edu.bstu.xyloteka.xyloteka.models.Sample;
import edu.bstu.xyloteka.xyloteka.models.Status;
import edu.bstu.xyloteka.xyloteka.models.TradeOffer;
import edu.bstu.xyloteka.xyloteka.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Repository
public interface TradeOfferRepository extends JpaRepository<TradeOffer, Long> {
    Optional<TradeOffer> findById(long id);
    List<TradeOffer> findByWhoRequestEmail(String whoRequest_email);
    List<TradeOffer> findByWhoRequestUsername(String whoRequest_username);
    List<TradeOffer> findByWhatAsk(Sample sample);
    List<TradeOffer> findByWhatAskId(long id);
    List<TradeOffer> findByWhatOffer(Sample sample);
    List<TradeOffer> findByWhatOfferId(long id);
    List<TradeOffer> findByStatus(Status status);
    List<TradeOffer> findByStatusName(String status);
}
