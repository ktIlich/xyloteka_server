package edu.bstu.xyloteka.xyloteka.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class TradeOffer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "who_request_id")
    private User whoRequest;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "what_offer_id")
    private Sample whatOffer;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "what_ask_id")
    private Sample whatAsk;
    private String description;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status_id")
    private Status status;

    public TradeOffer(User whoRequest, Sample whatOffer, Sample whatAsk, String description, Status status) {
        this.whoRequest = whoRequest;
        this.whatOffer = whatOffer;
        this.whatAsk = whatAsk;
        this.description = description;
        this.status = status;
    }
}
