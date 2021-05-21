package edu.bstu.xyloteka.xyloteka.models;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class BotanicDescription {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "divisio_id")
	private Division divisio;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "classis_id")
	private Classis classis;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ordo_id")
	private Ordo ordo;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "familiy_id")
	private Family familiy;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "genus_id")
	private Genus genus;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "species_id")
	private Species species;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Division getDivisio() {
		return this.divisio;
	}

	public void setDivisio(Division divisio) {
		this.divisio = divisio;
	}

	public Classis getClassis() {
		return this.classis;
	}

	public void setClassis(Classis classis) {
		this.classis = classis;
	}

	public Ordo getOrdo() {
		return this.ordo;
	}

	public void setOrdo(Ordo ordo) {
		this.ordo = ordo;
	}

	public Family getFamiliy() {
		return this.familiy;
	}

	public void setFamiliy(Family familiy) {
		this.familiy = familiy;
	}

	public Genus getGenus() {
		return this.genus;
	}

	public void setGenus(Genus genus) {
		this.genus = genus;
	}

	public Species getSpecies() {
		return this.species;
	}

	public void setSpecies(Species species) {
		this.species = species;
	}

}