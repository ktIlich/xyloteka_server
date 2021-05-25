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
	@JoinColumn(name = "family_id")
	private Family family;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "genus_id")
	private Genus genus;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "species_id")
	private Species species;

	public BotanicDescription(Division divisio, Classis classis, Ordo ordo, Family family, Genus genus, Species species) {
		this.divisio = divisio;
		this.classis = classis;
		this.ordo = ordo;
		this.family = family;
		this.genus = genus;
		this.species = species;
	}
}