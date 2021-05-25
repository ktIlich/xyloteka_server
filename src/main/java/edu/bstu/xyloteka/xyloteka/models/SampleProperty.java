package edu.bstu.xyloteka.xyloteka.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class SampleProperty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String density;
    private String hardness;
    private String shrinkage;

    public SampleProperty(String density, String hardness, String shrinkage) {
        this.density = density;
        this.hardness = hardness;
        this.shrinkage = shrinkage;
    }
}
