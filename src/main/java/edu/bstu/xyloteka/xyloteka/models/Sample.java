package edu.bstu.xyloteka.xyloteka.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Sample {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne()
    @JoinColumn(name = "botanic_description_id")
    private BotanicDescription botanicDescription;

    private String place;

    @OneToOne()
    @JoinColumn(name = "who_collect_id")
    private User whoCollect;

    @OneToOne()
    @JoinColumn(name = "who_define_id")
    private User whoDefine;

    private boolean trade;

    private java.util.Date collectDate;

    private String description;

    @OneToOne
    @JoinColumn(name = "property_id")
    private SampleProperty property;

    @OneToOne
    @JoinColumn(name = "names_id")
    private Names names;

    private boolean approve;

    public Sample(BotanicDescription botanicDescription, String place, User whoCollect, User whoDefine, boolean trade, Date collectDate, String description, SampleProperty property, Names names, boolean approve) {
        this.botanicDescription = botanicDescription;
        this.place = place;
        this.whoCollect = whoCollect;
        this.whoDefine = whoDefine;
        this.trade = trade;
        this.collectDate = collectDate;
        this.description = description;
        this.property = property;
        this.names = names;
        this.approve = approve;
    }
}