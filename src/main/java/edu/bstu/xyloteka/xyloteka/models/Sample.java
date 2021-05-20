package edu.bstu.xyloteka.xyloteka.models;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Sample {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "botanic_description_id")
    private BotanicDescription botanic_descriprtion;

    private String place;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "who_collect_id")
    private User who_collect;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "who_define_id")
    private User who_define;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "trade_offer_id")
    private TradeOffers isTrade;

    private java.util.Date collect_date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "photo_id")
    private Photo photo;

    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "property_id")
    private SampleProperty property;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "names_id")
    private Names names;

    public Sample() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BotanicDescription getBotanic_descriprtion() {
        return this.botanic_descriprtion;
    }

    public void setBotanic_descriprtion(BotanicDescription botanic_descriprtion) {
        this.botanic_descriprtion = botanic_descriprtion;
    }

    public String getPlace() {
        return this.place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public User getWho_collect() {
        return this.who_collect;
    }

    public void setWho_collect(User who_collect) {
        this.who_collect = who_collect;
    }

    public User getWho_define() {
        return this.who_define;
    }

    public void setWho_define(User who_define) {
        this.who_define = who_define;
    }

    public TradeOffers getIsTrade() {
        return this.isTrade;
    }

    public void setIsTrade(TradeOffers isTrade) {
        this.isTrade = isTrade;
    }

    public java.util.Date getCollect_date() {
        return this.collect_date;
    }

    public void setCollect_date(java.util.Date collect_date) {
        this.collect_date = collect_date;
    }

    public Photo getPhoto() {
        return this.photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SampleProperty getProperty() {
        return this.property;
    }

    public void setProperty(SampleProperty property) {
        this.property = property;
    }

    public Names getNames() {
        return this.names;
    }

    public void setNames(Names names) {
        this.names = names;
    }

}