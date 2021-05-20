package edu.bstu.xyloteka.xyloteka.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class BotanicDescription {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private int divisio;
	private int classis;
	private int ordo;
	private int familia;
	private int genus;
	private int species;

	public int getDivisio() {
		return divisio;
	}

	public void setDivisio(int divisio) {
		this.divisio = divisio;
	}

	public int getClassis() {
		return classis;
	}

	public void setClassis(int classis) {
		this.classis = classis;
	}

	public int getOrdo() {
		return ordo;
	}

	public void setOrdo(int ordo) {
		this.ordo = ordo;
	}

	public int getFamilia() {
		return familia;
	}

	public void setFamilia(int familia) {
		this.familia = familia;
	}

	public int getGenus() {
		return genus;
	}

	public void setGenus(int genus) {
		this.genus = genus;
	}

	public int getSpecies() {
		return species;
	}

	public void setSpecies(int species) {
		this.species = species;
	}
}