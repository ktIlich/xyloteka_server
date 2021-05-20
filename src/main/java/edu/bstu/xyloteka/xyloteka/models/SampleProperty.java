package edu.bstu.xyloteka.xyloteka.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class SampleProperty {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String density;
    private String hardness;
    private String shrinkage;

    public String getDensity() {
        return density;
    }

    public void setDensity(String density) {
        this.density = density;
    }

    public String getHardness() {
        return hardness;
    }

    public void setHardness(String hardness) {
        this.hardness = hardness;
    }

    public String getShrinkage() {
        return shrinkage;
    }

    public void setShrinkage(String shrinkage) {
        this.shrinkage = shrinkage;
    }
}
