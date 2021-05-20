package edu.bstu.xyloteka.xyloteka.models;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class TradeOffers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "who_request_id")
    private User who_request;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "who_offer_id")
    private User what_offer;
    // @ManyToOne(fetch = FetchType.EAGER)
    // @JoinColumn(name = "who_as_id")
    // private User what_ask;
    private String description;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status_id")
    private Status status;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getWho_request() {
        return this.who_request;
    }

    public void setWho_request(User who_request) {
        this.who_request = who_request;
    }

    public User getWhat_offer() {
        return this.what_offer;
    }

    public void setWhat_offer(User what_offer) {
        this.what_offer = what_offer;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

}
