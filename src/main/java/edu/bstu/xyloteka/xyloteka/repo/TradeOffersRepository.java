package edu.bstu.xyloteka.xyloteka.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import edu.bstu.xyloteka.xyloteka.models.TradeOffer;

@RepositoryRestResource(collectionResourceRel = "TradeOffers", path = "TradeOffers")
public interface TradeOffersRepository extends CrudRepository<TradeOffer, Long> {

}
